package P1;

import java.sql.*;

public class Driver {
    public static void main(String[] args) {
        try {
            String url = "jdbc:postgresql://localhost/ovchip?user=postgres&password=Bashar123?";
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();


            String query = "SELECT reiziger_id, voorletters,tussenvoegsel, achternaam, geboortedatum FROM reiziger ";
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("Alle Reizigers: ");
            while (rs.next()) {
                int id = Integer.parseInt(rs.getString("reiziger_id"));
                String voorletters = rs.getString("voorletters");
                String tussenvoegsel = rs.getString("tussenvoegsel");
                String achternaam = rs.getString("achternaam");
                Date geboortedatum = Date.valueOf(rs.getString("geboortedatum"));

                System.out.println("#"+id + ": "+ voorletters +" " +tussenvoegsel+" "+ achternaam + " "+"( " + geboortedatum + " )");


            }
            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
}