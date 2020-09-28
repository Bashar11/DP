package P4.JDBC;


import P4.Interface.AdresDao;
import P4.Interface.ReizigerDAO;
import P4.domein.Adres;
import P4.domein.Reiziger;

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
        if(adres == null){
            try {
                throw new Exception("Adres bestaat al");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        try {

            PreparedStatement statement = connection.prepareStatement("INSERT INTO adres(adres_id,postcode,huisnummer,straat,woonplaats,reiziger_id) VALUES (?,?,?,?,?,?) ");

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
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM adres");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("adres_id");
                String postcode = rs.getString("postcode");
                String huisnummer = rs.getString("huisnummer");
                String straat = rs.getString("straat");
                String woonplaats = rs.getString("woonplaats");
                int reiziger_id = rs.getInt("reiziger_id");


                System.out.println("#" + id + " :" + postcode + " " + huisnummer + " " + straat + " " + woonplaats + reiziger_id);
                adressen.add(new Adres(id, postcode, huisnummer, straat, woonplaats,rdao.findById(reiziger_id)));
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
            statement = connection.prepareStatement("SELECT * FROM adres WHERE reiziger_id = ?");
            statement.setInt(1, reiziger.getId());
            rs = statement.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("adres_id");
                String postcode = rs.getString("postcode");
                String huisnummer = rs.getString("huisnummer");
                String straat = rs.getString("straat");
                String woonplaats = rs.getString("woonplaats");
                System.out.println("#" + id + " :" + postcode + " " + huisnummer + " " + straat + " " + woonplaats);


                return new Adres(id, postcode, straat, huisnummer, woonplaats);
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

    @Override
    public Adres findById(int id){
        try{
            String query = "SELECT * FROM adres WHERE adres_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            // Resultaten
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {

                String straat = resultSet.getString("straat");
                String huisnummer = resultSet.getString("huisnummer");
                String postcode = resultSet.getString("postcode");
                String woonplaats = resultSet.getString("woonplaats");
                int reiziger_id = resultSet.getInt("reiziger_id");

                Adres adres = new Adres(id, straat, huisnummer, postcode, woonplaats, rdao.findById(reiziger_id));
                return adres;

            }
            // Sluit alles
            resultSet.close();
            preparedStatement.close();

            // Return

        }catch (SQLException e){
            System.out.println("Error " + e.getMessage());
        }

        return null;
    }

}