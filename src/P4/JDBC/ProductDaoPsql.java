package P4.JDBC;

import P4.Interface.OVChipkaartDao;
import P4.Interface.ProductDao;
import P4.domein.OVChipkaart;
import P4.domein.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoPsql implements ProductDao {
    private Connection connection;
    private static PreparedStatement statement;
    private static ResultSet rs;
    private OVChipkaartDao ovdao;


    public ProductDaoPsql(Connection connection) {
        this.connection = connection;
    }
    public void setOvdao(OVChipkaartDao ovdao){this.ovdao = ovdao;}


    @Override
    public boolean save(Product product) {
        try{
            statement = connection.prepareStatement("INSERT INTO product (product_nummer, naam, beschrijving,prijs) VALUES(?,?,?,?)");
            statement.setInt(1,product.getProductNr());
            statement.setString(2,product.getNaam());
            statement.setString(3,product.getBeschrijving());
            statement.setDouble(4,product.getPrijs());
            statement.executeUpdate();

            updateOVRelatie(product.getProductNr(), product.getOv());

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

            updateOVRelatie(product.getProductNr(),product.getOv());


        }
        catch(SQLException e){
            System.out.println("fout tijdens het updaten van de gegevens. " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Product product) {
        try{
            updateOvChipkaartRelatie(product);

            statement = connection.prepareStatement("DELETE FROM product WHERE product_nummer = ?");
            statement.setInt(1,product.getProductNr());
            statement.executeUpdate();
            System.out.println("Product is met succes verwijderd");
        }
        catch(SQLException e){
            System.out.println("Een fout bij het verwijderen van de gegevens. " + e.getMessage());
        }
        return false;
    }

    private void updateOVRelatie(int productnummer, List<OVChipkaart> kaarten) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM ov_chipkaart_product WHERE product_nummer = ?");
        statement.setInt(1, productnummer);
        statement.executeUpdate();
        statement.close();

        statement = connection.prepareStatement("INSERT INTO ov_chipkaart_product (product_nummer, kaart_nummer) VALUES (?, ?)");
        for(OVChipkaart kaart : kaarten) {
            statement.setInt(1, productnummer);
            statement.setInt(2, kaart.getKaartnummer());
            statement.executeUpdate();
        }
        statement.close();
    }


    private void updateOvChipkaartRelatie(Product productnummer) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM ov_chipkaart_product WHERE product_nummer = ?");
        statement.setInt(1, productnummer.getProductNr());
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public Product findByNr(int product) {
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

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart kaart) {
        List<Product> products = new ArrayList<>();
        try{
            statement = connection.prepareStatement("SELECT p.naam, p.beschrijving, p.prijs, p.product_nummer AS product_nummer\n" +
                    "                FROM product p JOIN ov_chipkaart_product ocp ON p.product_nummer = ocp.product_nummer\n" +
                    "                \n" +
                    "                WHERE ocp.kaart_nummer = ");
            statement.setInt(1,kaart.getKaartnummer());
            rs = statement.executeQuery();
            while(rs.next()){
                String naam = rs.getString("naam");
                String beschrijving = rs.getString("beschrijving");
                double prijs = rs.getDouble("prijs");
                int productNr = rs.getInt("product_nummer");
                int kaartNummer = rs.getInt("kaart_nummer");
                Product product = new Product(productNr,naam,beschrijving,prijs);

                OVChipkaart card = ovdao.findByKaartNr(kaartNummer);
                if(card != null){
                    product.addKaart(card);
                }

                products.add(product);

            }
            return products;

        }
        catch (SQLException e){
            e.getMessage();
        }
        return null;
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try{
            statement = connection.prepareStatement("SELECT * FROM product");
            rs = statement.executeQuery();

            while (rs.next()){
                int productNr = rs.getInt("product_nummer");
                String naam = rs.getString("naam");
                String beschrijving = rs.getString("beschrijving");
                double prijs = rs.getDouble("prijs");

                Product product1 = new Product(productNr,naam,beschrijving,prijs);
                products.add(product1);

            }
            return products;

        }
        catch(SQLException e){
            System.out.println("FOUT" + e.getMessage());

        }
        finally {
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

    //Een mogelijke uitvoer van vinden van product op basis van ov chipkaart
    // Dit is een voorbeeld van het het mogelijk kan , maar er wordt geen test geschreven hiervoor
    @Override
    public List<Product> findByOVChipkaartZonderKaarten(int kaartnummer) {
        List<Product> products = new ArrayList<>();
        try{
            statement = connection.prepareStatement("SELECT p.naam, p.beschrijving, p.prijs, p.product_nummer AS product_nummer\n" +
                    "                FROM product p JOIN ov_chipkaart_product ocp ON p.product_nummer = ocp.product_nummer\n" +
                    "                \n" +
                    "                WHERE ocp.kaart_nummer = ");
            statement.setInt(1,kaartnummer);
            rs = statement.executeQuery();
            while(rs.next()){
                String naam = rs.getString("naam");
                String beschrijving = rs.getString("beschrijving");
                double prijs = rs.getDouble("prijs");
                int productNr = rs.getInt("product_nummer");
                Product product = new Product(productNr,naam,beschrijving,prijs);

                products.add(product);
            }
            return products;
        }
        catch (SQLException e){
            e.getMessage();
        }
        return null;
    }
}
