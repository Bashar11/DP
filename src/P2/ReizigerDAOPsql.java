package P2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;





public class ReizigerDAOPsql implements ReizigerDAO{
    private Connection connection;
    private AdresDao adao;

    public ReizigerDAOPsql(Connection connection){
        this.connection = connection;


    }

    @Override
    public boolean save(Reiziger reiziger) {
        try{
            //er wordt een gebruik gemaakt van de PreparedStatement om beveiliging issues , voor een veiliger werk
            //een sql statement schrijven
            PreparedStatement statement = connection.prepareStatement("INSERT INTO reiziger (reiziger_id,voorletters,tussenvoegsel, achternaam, geboortedatum) VALUES (?,?,?,?,?)");
            statement.setInt(1,reiziger.getId());//parameter index voor id
            statement.setString(2, reiziger.getVoorletters()); //parameter index voor voorletter
            statement.setString(3,reiziger.getTussenvoegsel());//parameter index voor tussenvoegsel
            statement.setString(4,reiziger.getNaam());//parameter index voor achternaam
            statement.setDate(5,reiziger.getGeboortedatum());//parameter index voor geboortedatum

            statement.executeUpdate(); // excuten van de statement, laten runnen


        }
        catch(SQLException e){
            System.out.println("toevoegen is niet gelukt" + e.getMessage()); // een error als er iets fout is
            throw new RuntimeException("Fout"); //

        }
        return false;
    }

    @Override
    public boolean update(Reiziger reiziger) {
        try{
            //sql statement met update
            PreparedStatement statement = connection.prepareStatement("UPDATE reiziger SET voorletters = ?,tussenvoegsel = ?,achternaam = ?,geboortedatum = ? WHERE reiziger_id = ? ");

            statement.setString(1,reiziger.getVoorletters());
            statement.setString(2,reiziger.getTussenvoegsel());
            statement.setString(3,reiziger.getNaam());
            statement.setDate(4,reiziger.getGeboortedatum());
            statement.setInt(5,reiziger.getId());

            int i = statement.executeUpdate();
            System.out.println(i+" gegevens zijn succesvol gewijzigd");

        }catch(SQLException e){
            System.out.println("Updaten van info was niet gelukt " + e.getMessage());
        }
        return false ;


    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try{
            //verwijderen
            PreparedStatement statement = connection.prepareStatement("DELETE FROM reiziger WHERE reiziger_id = ?");
            statement.setInt(1,reiziger.getId());
            int i = statement.executeUpdate();//bij verwijderen, updaten , inserten altijd ecuteUpdate gebruiken
            System.out.println(i+" Reiziger is met succes verwijderd");
            return i > 0;
        }catch(SQLException e){
            System.out.println("Verwijderen van reiziger is niet gelukt" + e.getMessage());

        }
        return false;
    }

    @Override
    public Reiziger findById(int id) {
        PreparedStatement statement = null;
        ResultSet rs = null;

        try{
            //statement maken op basis van id
            statement = connection.prepareStatement("SELECT voorletters, tussenvoegsel, achternaam, geboortedatum FROM reiziger WHERE reiziger_id = ?");
            statement.setInt(1,id);

            rs = statement.executeQuery();// bij selecteren gebruiken we excuteQuery

            if (rs.next()){

                String voorletters = rs.getString("voorletters");
                String tussenvoegsel = rs.getString("tussenvoegsel");
                String achternaam = rs.getString("achternaam");
                Date geboortedatum = Date.valueOf(rs.getString("geboortedatum"));


                System.out.println("#"+id + ": "+ voorletters +" " +tussenvoegsel+" "+ achternaam + " "+"( " + geboortedatum + " )");

                return new Reiziger(id, voorletters, tussenvoegsel, achternaam, geboortedatum);
            }
        }
        catch(SQLException e){//sql exception
            System.out.println("vinden van reiziger is niet gelukt");
        } finally {
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
        }

        return null;
    }

    @Override
    public List<Reiziger> findByGbDatum(String datum) {

        try{

            List<Reiziger> result = new ArrayList<Reiziger>(); //arraylist aanmaken met result die we willen krijgen
            //sql statemnt maken op basis van geoortedatum
            PreparedStatement statement = connection.prepareStatement("SELECT reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum FROM reiziger WHERE geboortedatum = ?");
            statement.setDate(1, Date.valueOf(datum));//Date.valueOf gebruiken om de geboortedatum te returnen
            ResultSet rs = statement.executeQuery();//statement laten draaien

            while(rs.next()){//returnen van de gegevens
                int id = Integer.parseInt(rs.getString("reiziger_id"));
                String voorletters = rs.getString("voorletters");
                String tussenvoegsel = rs.getString("tussenvoegsel");
                String achternaam = rs.getString("achternaam");
                Date geboortedatum = Date.valueOf(rs.getString("geboortedatum"));

                System.out.println("#"+id + ": "+ voorletters +" " +tussenvoegsel+" "+ achternaam + " "+"( " + geboortedatum + " )");
            }


            rs.close();
            statement.close();
            return result;//returnen van de array

        }
        catch(SQLException e){
            System.out.println("vinden van reiziger was niet gelukt");
            throw new RuntimeException("Fout");
        }


    }

    @Override
    public List<Reiziger> findAll() {
        try {
            List<Reiziger> result = new ArrayList<Reiziger>();//array aanmaken
            //sql statement om alle reizigers te returnen
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM reiziger");

            ResultSet rs = statement.executeQuery();
            while(rs.next()){

                int id = Integer.parseInt(rs.getString("reiziger_id"));
                String voorletters = rs.getString("voorletters");
                String tussenvoegsel = rs.getString("tussenvoegsel");
                String achternaam = rs.getString("achternaam");
                Date geboortedatum = Date.valueOf(rs.getString("geboortedatum"));

                System.out.println("#"+id + ": "+ voorletters +" " +tussenvoegsel+" "+ achternaam + " "+"( " + geboortedatum + " )");
                result.add(new Reiziger(id,voorletters,tussenvoegsel,achternaam,geboortedatum));

            }

            rs.close();

            statement.close();
            return result;
        } catch (SQLException e) {
            System.out.println("Weergeven is niet gelukt" + e.getMessage());
            throw new RuntimeException("weergeven van reiziger is niet gelukt");
        }

    }

}
