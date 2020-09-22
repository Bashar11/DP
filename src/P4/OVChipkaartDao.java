package P4;

import java.util.List;

public interface OVChipkaartDao {
    public List<OVChipkaart> findByReiziger(Reiziger reiziger);
    public boolean save(OVChipkaart ovkaart) ;
    public boolean update(OVChipkaart ovkaart);
    public boolean delete(OVChipkaart ovkaart);
    public List<OVChipkaart>findAll();
    public OVChipkaart findByKaartNr(int nr);

}
