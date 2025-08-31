package co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.adapters;

import co.com.tokenization.tokenization_api.domain.model.Product;
import co.com.tokenization.tokenization_api.domain.model.gateway.ProductRepository;
import co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.entity.ProductEntity;
import co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.repository.ProductJPARepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {

    private final ProductJPARepository spring;

    @Override
    public List<Product> findAll() {
        return spring.findAll().stream().map(e -> new Product(e.getId(), e.getName(), e.getStock())).collect(Collectors.toList());
    }

    @Override
    public List<Product> findByNameContaining(String term) {
        return spring.findByNameContainingIgnoreCase(term).stream().map(e -> new Product(e.getId(), e.getName(), e.getStock())).collect(Collectors.toList());
    }

    @Override
    public Product save(Product p) {
        ProductEntity e = new ProductEntity();
        e.setId(p.getId());
        e.setName(p.getName());
        e.setStock(p.getStock());
        ProductEntity saved = spring.save(e);
        return new Product(saved.getId(), saved.getName(), saved.getStock());
    }

    @Override
    public Optional<Product> findById(Long id) {
        return spring.findById(id).map(e -> new Product(e.getId(), e.getName(), e.getStock()));
    }
}
