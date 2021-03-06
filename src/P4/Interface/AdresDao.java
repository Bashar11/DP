package P4.Interface;

import P4.domein.Adres;
import P4.domein.Reiziger;

import java.util.List;

public interface AdresDao {
    public boolean save(Adres adres);
    public boolean update(Adres adres);
    public boolean delete(Adres adres);
    public Adres findByReiziger(Reiziger reiziger);
    public Adres findById(int id);
    public List<Adres> findAll();
}
