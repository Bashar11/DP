package P4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDaoPsql implements ProductDao{
    private Connection connection;
    private static PreparedStatement statement;
    private static ResultSet rs;


    public ProductDaoPsql(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean save(Product product) {
        try{
            statement = connection.prepareStatement("INSERT INTO product (product_nummer, naam, beschrijving,prijs) VALUES(?,?,?,?,?)");
            statement.setInt(1,product.getProductNr());
            statement.setString(2,product.getNaam());
            statement.setString(3,product.getBeschrijving());
            statement.setDouble(4,product.getPrijs());
            statement.executeUpdate();

            System.out.println("Product is aangemaakt");


        }
        catch(SQLException e){
            System.out.println("Een fout opgetreden tijdens aanmaken van een product. " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Product product) {
        try{
            statement = connection.prepareStatement("UPDATE product SET naam = ? , beschrijving = ?, prijs = ? WHERE product_nummer = ?");
            statement.setString(1,product.getNaam());
            statement.setString(2,product.getBeschrijving());
            statement.setDouble(3,product.getPrijs());
            statement.setInt(4,product.getProductNr());
            statement.executeUpdate();
            System.out.println("Gegevens zijn met succes verwerkt...");


        }
        catch(SQLException e){
            System.out.println("fout tijdens het updaten van de gegevens. " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Product product) {
        try{
            statement = connection.prepareStatement("DELETE FROM product WHERE product_nummer = ?");
            statement.setInt(1,product.getProductNr());
            System.out.println("Product is met succes verwijderd");
        }
        catch(SQLException e){
            System.out.println("Een fout bij het verwijderen van de gegevens. " + e.getMessage());
        }
        return false;
    }

    @Override
    public Product findByNr(Product product) {
        try{
            statement = connection.prepareStatement("SELECT * FROM product WHERE product_nummer = ?");
            rs = statement.executeQuery();
            if(rs.next()){
                int productNr = rs.getInt("product_nummer");
                String naam = rs.getString("naam");
                String beschrijving = rs.getString("beschrijving");
                double prijs = rs.getDouble("prijs");

                Product product1 = new Product(productNr,naam,beschrijving,prijs);
                return product1;
            }

        }
        catch(SQLException e){
            e.getMessage();
        }
        return null;
    }
}
