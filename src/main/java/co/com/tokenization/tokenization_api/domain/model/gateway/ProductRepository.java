package co.com.tokenization.tokenization_api.domain.model.gateway;

import co.com.tokenization.tokenization_api.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findByNameContaining(String name);
    Optional<Product> findById(Long id);
}
