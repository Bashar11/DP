package P4;

import P4.Interface.AdresDao;
import P4.Interface.OVChipkaartDao;
import P4.Interface.ProductDao;
import P4.Interface.ReizigerDAO;
import P4.JDBC.AdresDaoPsql;
import P4.JDBC.OVChipkaartDaoPsql;
import P4.JDBC.ProductDaoPsql;
import P4.JDBC.ReizigerDAOPsql;
import P4.domein.Adres;
import P4.domein.OVChipkaart;
import P4.domein.Product;
import P4.domein.Reiziger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {
    private static Connection connection;

    static Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf("1981-03-14"));
    static Reiziger reiziger = new Reiziger(17,"B","","Chamoun",Date.valueOf("1998-04-25"));
    static Adres adres = new Adres(8,"1321JN","Hanstraat","2","Almere",reiziger);
    static OVChipkaart kaart = new OVChipkaart(12345,Date.valueOf("2020-01-01"),2,20.0,reiziger);
    static Product product = new Product(7,"30%korting","een 30% korting product", 50.0);


    public static void main(String[]args){

    getConnection();

        try {
            ReizigerDAOPsql rdao = new ReizigerDAOPsql(connection);
            AdresDaoPsql adao = new AdresDaoPsql(connection);
            OVChipkaartDaoPsql ovdao = new OVChipkaartDaoPsql(connection);
            ProductDaoPsql pdao = new ProductDaoPsql(connection);
            adao.setRdao(rdao);
            rdao.setAdresDao(adao);
            ovdao.setRdao(rdao);
            rdao.setOvdao(ovdao);
            ovdao.setPdao(pdao);

            adao.delete(adres);
            rdao.delete(sietske);
            rdao.delete(reiziger);
            ovdao.delete(kaart);
            pdao.delete(product);
            testReizigerDAO(rdao);
            testAdresDao(adao);
            testOVChipKaartDao(ovdao,rdao);
            testProductDao(pdao,ovdao);


        } catch (SQLException e) {
            e.printStackTrace();
        }

    closeConnection();

    }

    private static void getConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost/ovchip?user=postgres&password=Bashar123?");


        } catch (SQLException e) {
            System.out.println("Error: cannot connect to database" + e.getMessage());
            throw new RuntimeException("cannot connect to database");

        }

    }

    private static void closeConnection(){
        try{
            if(connection !=null){
                connection.close();
                connection = null;
            }
        }
        catch(SQLException e){
            System.out.println("Error : cannot close connection" + e.getMessage());
        }

    }

    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {

        System.out.println("\n---------- Test ReizigerDAO -------------");
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("----Reizigers zonder adres-----");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println("------------");

        // Maak een nieuwe reiziger aan en persisteer deze in de database

        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() \n");
        rdao.save(sietske);
        rdao.save(reiziger);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.



        //updaten van een reiziger

        sietske.setVoorletters("C");
        sietske.setTussenvoegsel("");
        sietske.setNaam("Chamoun");
        sietske.setGeboortedatum(Date.valueOf("1998-04-25"));
        rdao.update(sietske);

        //vinden op basis van id
        System.out.println("[TEST] Vinden op basis van id ");
        System.out.println("---------");
        rdao.findById(1);


        //reiziger vinden op basis van geboorteedatum
        System.out.println("[TEST] Vinden op basis van geboortedatum");
        System.out.println("---------");
        rdao.findByGbDatum("2002-12-03");
        System.out.println("\n");


        //verwijderen test
        rdao.delete(sietske);
        System.out.println("-----------");


    }
    private static void testAdresDao(AdresDao adao){
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende adressen:");

        adao.findAll();
//        for (Adres a : adressen) {
//            System.out.println(a);
//        }
        //adres aanmaken
        System.out.println();


        System.out.println("[TEST] Adres aanmaken---------");
        adao.save(adres);
        System.out.println("-------------");
        System.out.println("[TEST] Vinden van adres op basis van reiziger---------------");
        adres = adao.findByReiziger(reiziger);

        System.out.println("[TEST] Vinden van adres op basis van id");
        System.out.println("--------");
        Adres adres1 = adao.findById(3);
        System.out.println(adres1);



        System.out.println("-----UPDATEN-----");
        adres.setPostCode("1333AA");
        adres.setStraat("KilStraat");
        adres.setHuisNr("13");
        adres.setWoonplaats("Utrecht");
        adao.update(adres);

        //verwijderen testen
        System.out.println("-------VERWIJDEREN--------");
        adao.delete(adres);

        //


    }
    private static void testOVChipKaartDao(OVChipkaartDao ovdao, ReizigerDAO rdao){
        List<OVChipkaart> kaarten = ovdao.findAll();
        System.out.println("--------ALLE KAARTEN--------");
        for(OVChipkaart kaart: kaarten){
            System.out.println(kaart);
        }

        System.out.println("--------INSERTEN--------");
        ovdao.save(kaart);


        System.out.println("--------VINDEN OP BASIS VAN REIZIGER---------");
        ovdao.findByReiziger(reiziger);
        System.out.println(kaart);

        System.out.println("--------UPDATEN VAN OV--------");
        kaart.setKaartnummer(12344);
        kaart.setGeldigheid(Date.valueOf("2021-01-01"));
        kaart.setSaldo(40.0);
        kaart.setKlasse(1);
        ovdao.update(kaart);
        System.out.println(kaart);

        System.out.println("---------VERWIJDEREN---------");
        ovdao.delete(kaart);

        System.out.println("--------VINDEN OP BASIS VAN KAARTNUMMER--------");
        ovdao.findByKaartNr(46392);




        System.out.println();


    }

    private static void testProductDao(ProductDao pdao, OVChipkaartDao ovdao){
        List<Product> products = pdao.findAll();
        for(Product product : products){
            System.out.println(product);
        }

        System.out.println("[TEST]-----Product aanmaken-----");
        OVChipkaart ov = ovdao.findByKaartNr(35283);
        product.addKaart(ov);
        pdao.save(product);
        System.out.println();




        System.out.println("[TEST]-----Product Updaten-----");
        product.setNaam("AndereKortingNaam");
        product.setBeschrijving("Dit is een andere korting product");
        product.setPrijs(0.0);

        pdao.update(product);

        System.out.println(product);

        pdao.delete(product);

    }


}