package P2;

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


    public static void main(String[]args){

    getConnection();

        try {
            ReizigerDAOPsql rdao = new ReizigerDAOPsql(connection);
            AdresDaoPsql adao = new AdresDaoPsql(connection);
            adao.setRdao(rdao);

            adao.delete(adres);
            rdao.delete(sietske);
            rdao.delete(reiziger);
            testReizigerDAO( rdao);
            testAdresDao(adao);
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

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database

        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
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
        System.out.println("Vinden op basis van id ");
        rdao.findById(77);

        //reiziger vinden op basis van geboorteedatum
        System.out.println("Vinden op basis van geboortedatum");
        rdao.findByGbDatum("2002-12-03");

        //verwijderen test
        rdao.delete(sietske);


    }
    private static void testAdresDao(AdresDao adao){
        List<Adres> adressen = adao.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende reizigers:");
        for (Adres a : adressen) {
            System.out.println(a);
        }
        //adres aanmaken
        System.out.println();


        System.out.println("test");
        adao.save(adres);

        adres = adao.findByReiziger(reiziger);
        System.out.println(adres.getId());


        adres.setPostCode("1333AA");
        adres.setStraat("KilStraat");
        adres.setHuisNr("13");
        adres.setWoonplaats("Utrecht");
        adao.update(adres);

        //verwijderen testen

        adao.delete(adres);

        //


    }


}