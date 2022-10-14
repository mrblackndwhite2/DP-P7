package patterns;

import domein.*;

import java.util.List;

public interface ProductDAO {
    boolean save(Product p);

    boolean update(Product p);

    boolean delete(Product p);

    Product findById(int id);

    List<Product> findAll();
}
