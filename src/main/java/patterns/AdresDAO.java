package patterns;

import domein.*;

import java.util.List;

public interface AdresDAO {
    boolean save(Adres adres);

    boolean update(Adres adres);

    boolean delete(Adres adres);

    Adres findByReiziger(Reiziger reiziger);

    Adres findById(int id);

    List<Adres> findAll();
}
