package co.com.tokenization.tokenization_api.domain.model.gateway;

import co.com.tokenization.tokenization_api.domain.model.Product;
import org.aspectj.apache.bcel.classfile.Module;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();
    List<Product> findByNameContaining(String term);
    Product save(Product p);
    Optional<Product> findById(Long id);
}

