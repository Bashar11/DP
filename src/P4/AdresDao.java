package P4;

import java.util.List;

public interface AdresDao {
    public boolean save(Adres adres);
    public boolean update(Adres adres);
    public boolean delete(Adres adres);
    public Adres findByReiziger(Reiziger reiziger);
    public Adres findById(int id);
    public List<Adres> findAll();
}
