package snacknroll.dao;

import snacknroll.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductDAO {
  List<Product> findAll();
  List<Product> findByCategory(String categoria);
  List<Product> searchByQuery(String q);
  Optional<Product> findById(long id_prodotto);
}
