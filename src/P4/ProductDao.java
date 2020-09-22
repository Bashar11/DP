package P4;

public interface ProductDao {
    public boolean save(Product product);
    public boolean update(Product product);
    public boolean delete(Product product);
    public Product findByNr(Product product);

    
}
