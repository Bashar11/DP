package P2;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresDaoPsql implements AdresDao {
    private Connection connection;
    private ReizigerDAO rdao;

    public AdresDaoPsql(Connection connection) {
        this.connection = connection;

    }

    public void setRdao(ReizigerDAO reizigerDAO) {
        this.rdao = reizigerDAO;
    }


    public boolean save(Adres adres) {
        try {

            PreparedStatement statement = connection.prepareStatement("INSERT INTO ADRES(adres_id,postcode,huisnummer,straat,woonplaats,reiziger_id) VALUES (?,?,?,?,?,?) ");

            statement.setInt(1, adres.getId());
            statement.setString(2, adres.getPostCode());
            statement.setString(3, adres.getStraat());
            statement.setString(4, adres.getHuisNr());
            statement.setString(5, adres.getWoonplaats());
            statement.setInt(6, adres.getReiziger().getId());

            int i = statement.executeUpdate();
            System.out.println(i + " Adres aangemaakt");

        } catch (SQLException e) {
            System.out.println("Toevoegen van adres is niet gelukt " + e.getMessage());
            throw new RuntimeException("Een internal fout");
        }

        return false;
    }

    public boolean update(Adres adres) {
        try {

            PreparedStatement statement = connection.prepareStatement("UPDATE adres SET adres_id=?, postcode = ?,huisnummer = ?,straat = ?,woonplaats = ?, reiziger_id= ? WHERE adres_id = ? ");
            statement.setInt(1, adres.getId());
            statement.setString(2, adres.getPostCode());
            statement.setString(3, adres.getHuisNr());
            statement.setString(4, adres.getStraat());
            statement.setString(5, adres.getWoonplaats());
            statement.setInt(6, adres.getReiziger().getId());
            statement.setInt(7, adres.getId());

            int i = statement.executeUpdate();
            System.out.println(i + "Adres geupdate");

        } catch (SQLException e) {
            System.out.println("Updaten van het adres was niet gelukt. " + e.getMessage());
        }
        return false;
    }

    public boolean delete(Adres adres) {
        try {

            PreparedStatement statement = connection.prepareStatement("DELETE FROM adres WHERE adres_id = ?");
            statement.setInt(1, adres.getId());
            int i = statement.executeUpdate();
            System.out.println(i + " adres verwijderd");
        } catch (SQLException e) {
            System.out.println("Verwijderen van een adres was niet gelukt" + e.getMessage());

        }
        return false;
    }


    public List<Adres> findAll() {

        try {
            List<Adres> adressen = new ArrayList<Adres>();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ADRES");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("adres_id");
                String postcode = rs.getString("postcode");
                String huisnummer = rs.getString("huisnummer");
                String straat = rs.getString("straat");
                String woonplaats = rs.getString("woonplaats");


                System.out.println("#" + id + " :" + postcode + " " + huisnummer + " " + straat + " " + woonplaats);
                adressen.add(new Adres(id, postcode, huisnummer, straat, woonplaats));
            }

            rs.close();

            statement.close();
            return adressen;
        } catch (SQLException e) {
            System.out.println("iets ging mis" + e.getMessage());
            throw new RuntimeException("Een fout melding");
        }
    }

    public Adres findByReiziger(Reiziger reiziger) {
        PreparedStatement statement = null;
        ResultSet rs = null;


        try {
            statement = connection.prepareStatement("SELECT * FROM ADRES WHERE reiziger_id = ?");
            statement.setInt(1, reiziger.getId());
            rs = statement.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("adres_id");
                String postcode = rs.getString("postcode");
                String huisnummer = rs.getString("huisnummer");
                String straat = rs.getString("straat");
                String woonplaats = rs.getString("woonplaats");
                int reiziger_id = rs.getInt("reiziger_id");
                System.out.println("reiziger id = " + reiziger_id);
                System.out.println("#" + id + " :" + postcode + " " + huisnummer + " " + straat + " " + woonplaats);


                return new Adres(id, postcode, straat, huisnummer, woonplaats, rdao.findById(reiziger.getId()));
            }
        } catch (SQLException e) {
            System.out.println("een fout opgetreden met het vinden van adres " + e.getMessage());
            throw new RuntimeException("Een error");
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


}
