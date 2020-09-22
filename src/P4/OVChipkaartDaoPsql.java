package P4;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDaoPsql implements OVChipkaartDao{

    private Connection connection;
    private ReizigerDAO rdao;

    public OVChipkaartDaoPsql(Connection connection){
        this.connection = connection;
    }

    public void setRdao(ReizigerDAO reizigerDAO) {
        this.rdao = reizigerDAO;
    }




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

            int i = statement.executeUpdate();
            System.out.println(i + " Gegevens zijn met succes gewijzigd");

        }catch(SQLException e){
            System.out.println("Er is een fout opgetreden met het aanmaken van de gegevens. "+ e.getMessage()
            );
            throw new RuntimeException("Een fout tijdens operatie");
        }
        return false;
    }

    @Override
    public boolean delete(OVChipkaart ovkaart) {

        try{
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

                System.out.println("KaartNummer: "+kaartNummer +". Geldigheid: " + geldigheid+". Klasse: "+klasse + ". Saldo: "+ saldo );
                return new OVChipkaart(kaartNummer,geldigheid,klasse,saldo);
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

}

