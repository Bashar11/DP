package P4.JDBC;


import P4.Interface.OVChipkaartDao;
import P4.Interface.ProductDao;
import P4.Interface.ReizigerDAO;
import P4.domein.OVChipkaart;
import P4.domein.Product;
import P4.domein.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDaoPsql implements OVChipkaartDao {

    private Connection connection;
    private ReizigerDAO rdao;
    private ProductDao pdao;

    public OVChipkaartDaoPsql(Connection connection){
        this.connection = connection;
    }

    public void setRdao(ReizigerDAO reizigerDAO) {
        this.rdao = reizigerDAO;
    }
    public void setPdao(ProductDao pdao){this.pdao = pdao;}





    @Override
    public boolean save(OVChipkaart ovkaart) {
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO ov_chipkaart (kaart_nummer,geldig_tot,klasse,saldo,reiziger_id) VALUES (?,?,?,?,?)");
            statement.setInt(1,ovkaart.getKaartnummer());
            statement.setDate(2,ovkaart.getGeldigheid());
            statement.setInt(3,ovkaart.getKlasse());
            statement.setDouble(4,ovkaart.getSaldo());
            statement.setInt(5,ovkaart.getReiziger().getId());

            int i = statement.executeUpdate();
            System.out.println(i + " Gegevens zijn met succes aangemaakt");

            updateProductRelatie(ovkaart.getKaartnummer(), ovkaart.getProducten());

        }catch(SQLException e){
            System.out.println("Er is een fout opgetreden met het aanmaken van de gegevens. "+ e.getMessage()
          );
            throw new RuntimeException("Een fout tijdens operatie");
        }
        return false;
        }

    @Override
    public boolean update(OVChipkaart ovkaart) {
        try{
            PreparedStatement statement = connection.prepareStatement("UPDATE ov_chipkaart SET kaart_nummer=?,geldig_tot=?,klasse=?,saldo = ?,reiziger_id =? WHERE kaart_nummer = ?");
            statement.setInt(1,ovkaart.getKaartnummer());
            statement.setDate(2,ovkaart.getGeldigheid());
            statement.setInt(3,ovkaart.getKlasse());
            statement.setDouble(4,ovkaart.getSaldo());
            statement.setInt(5,ovkaart.getReiziger().getId());
            statement.setInt(6,ovkaart.getKaartnummer());

            updateProductRelatie(ovkaart.getKaartnummer(), ovkaart.getProducten());

            int i = statement.executeUpdate();
            System.out.println(i + " Gegevens zijn met succes gewijzigd");

        }catch(SQLException e){
            System.out.println("Er is een fout opgetreden met het aanmaken van de gegevens. "+ e.getMessage()
            );
            throw new RuntimeException("Een fout tijdens operatie");
        }
        return false;
    }

    private void updateProductRelatie(int kaartnummer, List<Product> products) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM ov_chipkaart_product WHERE kaart_nummer = ?");
        statement.setInt(1, kaartnummer);
        statement.executeUpdate();
        statement.close();

        statement = connection.prepareStatement("INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer) VALUES (?, ?)");
        for(Product product : products) {
            statement.setInt(1, kaartnummer);
            statement.setInt(2, product.getProductNr());
            statement.executeUpdate();
        }
        statement.close();
    }

    @Override
    public boolean delete(OVChipkaart ovkaart) {

        try{
            //leegmaken van de relaties met product
            updateProductRelatie(ovkaart.getKaartnummer(), new ArrayList<>());

           PreparedStatement statement = connection.prepareStatement("DELETE FROM ov_chipkaart WHERE kaart_nummer =?");
            statement.setInt(1,ovkaart.getKaartnummer());
            int i = statement.executeUpdate();
            System.out.println(i + " Gegevens zijn met succes verwijderd");

        }
        catch(SQLException e){
            System.out.println("Een fout opgetreden tijdens het verwijderen van het ov" );
            e.getMessage();
        }
        return false;
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {

        PreparedStatement statement = null;
        ResultSet rs = null;
        try{
            List<OVChipkaart> lijstKaarten = new ArrayList<>();

            statement = connection.prepareStatement("SELECT * FROM ov_chipkaart WHERE reiziger_id = ?");
            statement.setInt(1,reiziger.getId());
            rs = statement.executeQuery();

            if(rs.next()){
                int nummer = rs.getInt("kaart_nummer");
                Date geldigheid = Date.valueOf(String.valueOf(rs.getDate("geldig_tot")));
                int klasse = rs.getInt("klasse");
                double saldo = rs.getDouble("saldo");
                int reiziger_id = rs.getInt("reiziger_id");
                System.out.println("reiziger id = " + reiziger_id);
                System.out.println("#" + nummer + " :" + geldigheid + " " + klasse + " " + saldo + " " );


                 OVChipkaart ov = new OVChipkaart(nummer, geldigheid, klasse, saldo, rdao.findById(reiziger.getId()));
                 lijstKaarten.add(ov);


            }
            return lijstKaarten;
        }catch(SQLException e){
            System.out.println("Een fout opgetreden met het vinden van informatie"+e.getMessage());
            throw new RuntimeException("Error");

        }finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();

                }
            } catch (Exception exc) {
                //exception bij closen van resultset
            }
        return null;
    }

    }
    @Override
    public List<OVChipkaart> findAll() {
        PreparedStatement statement = null;
        ResultSet rs = null;
        try{
            List<OVChipkaart> lijst = new ArrayList<>();
            statement = connection.prepareStatement("SELECT kaart_nummer, geldig_tot, klasse, saldo FROM ov_chipkaart");
            rs = statement.executeQuery();
            System.out.println("Alle Ovkaarten: ");
            while (rs.next()){
                int kaartnummer = rs.getInt("kaart_nummer");
                Date geldigheid = rs.getDate("geldig_tot");
                int klasse = rs.getInt("klasse");
                double saldo = rs.getDouble("saldo");

                System.out.println("#: " + kaartnummer+ " "+ geldigheid+" "+ klasse+ " "+ saldo );
                lijst.add(new OVChipkaart(kaartnummer,geldigheid,klasse,saldo));
                return lijst;
            }


        }
        catch(SQLException exc){
            System.out.println("Een fout opgetreden met het vinden van de gegevens van het ov. "+ exc.getMessage());
        }finally {
            try{
                if(rs!=null){
                    rs.close();
                }
                if(statement != null){
                    statement.close();
                }
            }catch(Exception e){

            }
        }
        return null;
    }

    @Override
    public OVChipkaart findByKaartNr( int nr) {
        PreparedStatement statement = null ;
        ResultSet rs = null;

        try{
            statement = connection.prepareStatement("SELECT kaart_nummer,geldig_tot,klasse,saldo FROM ov_chipkaart WHERE kaart_nummer = ?");
            statement.setInt(1,nr);
            rs = statement.executeQuery();

            if(rs.next()){
                int kaartNummer = rs.getInt("kaart_nummer");
                Date geldigheid = rs.getDate("geldig_tot");
                int klasse = rs.getInt("klasse");
                double saldo = rs.getDouble("saldo");

                List<Product> products = pdao.findByOVChipkaartZonderKaarten(kaartNummer);

                System.out.println("KaartNummer: "+kaartNummer +". Geldigheid: " + geldigheid+". Klasse: "+klasse + ". Saldo: "+ saldo );
                OVChipkaart result = new OVChipkaart(kaartNummer,geldigheid,klasse,saldo);
                for(Product product : products) {
                    List<OVChipkaart> ovChipkaarts = findByProduct(product);
                    for(OVChipkaart ovChipkaart : ovChipkaarts) {
                        product.addKaart(ovChipkaart);
                    }
                    result.addProduct(product);
                }

                return result;
            }
        }
        catch(SQLException e){
            System.out.println("Vinden van gegevens is niet gelukt.");
            e.getMessage();

        }
        finally {
            try{
                if(rs != null){
                    rs.close();
                }
                if (statement != null){
                    statement.close();
                }
            }
            catch (Exception e){
                //exception bij sluiten van ResultSet en PreparedStatement
            }
        }
        return null;
    }

    @Override
    public List<OVChipkaart> findByProduct(Product product) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<OVChipkaart> lijstKaarten = new ArrayList<>();
        try{
             statement = connection.prepareStatement("SELECT ov.kaart_nummer, ov.geldig_tot,ov.klasse,ov.saldo,ov.reiziger_id\n"+
                    "FROM ov_chipkaart ov JOIN ov_chipkaart_product ovc ON ov.kaart_nummer = ovc.kaart_nummer\n"+
                    "WHERE product_nummer = ?");
            statement.setInt(1,product.getProductNr());

             rs = statement.executeQuery();
            while (rs.next()){
                int kaartNr = rs.getInt("kaart_nummer");
                Date geldigheid = rs.getDate("geldig_tot");
                int klasse = rs.getInt("klasse");
                double saldo = rs.getDouble("saldo");
                int reiziger_id = rs.getInt("reiziger_id");
                int productNr = rs.getInt("product_nummer");
                OVChipkaart ovChipkaart = new OVChipkaart(kaartNr,geldigheid,klasse,saldo,rdao.findById(reiziger_id));

                Product product1 = pdao.findByNr(productNr);
                if(product1 != null){
                    ovChipkaart.addProduct(product1);
                }
                lijstKaarten.add(ovChipkaart);
            }
            return lijstKaarten;

        }
        catch (SQLException e){
            System.out.println("Fout met weergeven van resultaten. " + e.getMessage());
        }
        finally {
            try{
                if(rs != null){
                    rs.close();
                }
                if (statement != null){
                    statement.close();
                }
            }
            catch (Exception e){
                //exception bij sluiten van ResultSet en PreparedStatement
            }
        }
        return null;
    }


}

