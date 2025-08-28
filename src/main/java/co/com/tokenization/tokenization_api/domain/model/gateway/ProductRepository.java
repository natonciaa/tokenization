package co.com.tokenization.tokenization_api.domain.model.gateway;

import co.com.tokenization.tokenization_api.domain.model.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll();
    List<Product> findByNameContaining(String term);
    Product save(Product p);
    Product findById(Long id);
}

