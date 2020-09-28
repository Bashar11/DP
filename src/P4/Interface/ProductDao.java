package P4.Interface;

import P4.domein.OVChipkaart;
import P4.domein.Product;

import java.util.List;

public interface ProductDao {
    public boolean save(Product product);
    public boolean update(Product product);
    public boolean delete(Product product);
    public Product findByNr(int product);
    public List<Product> findByOVChipkaart(OVChipkaart kaart);
    public List<Product> findAll();
    public List<Product> findByOVChipkaartZonderKaarten(int kaartnummer);
    
}
