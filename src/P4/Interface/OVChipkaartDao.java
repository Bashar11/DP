package P4.Interface;

import P4.domein.OVChipkaart;
import P4.domein.Product;
import P4.domein.Reiziger;

import java.util.List;

public interface OVChipkaartDao {
    public List<OVChipkaart> findByReiziger(Reiziger reiziger);
    public boolean save(OVChipkaart ovkaart) ;
    public boolean update(OVChipkaart ovkaart);
    public boolean delete(OVChipkaart ovkaart);
    public List<OVChipkaart>findAll();
    public OVChipkaart findByKaartNr(int nr);
    public List<OVChipkaart> findByProduct(Product product);

}
