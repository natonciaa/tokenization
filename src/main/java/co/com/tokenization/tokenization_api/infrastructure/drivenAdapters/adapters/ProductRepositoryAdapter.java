package co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.adapters;

import co.com.tokenization.tokenization_api.domain.model.Product;
import co.com.tokenization.tokenization_api.domain.model.gateway.ProductRepository;

import co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.repository.ProductJPARepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductRepositoryAdapter implements ProductRepository {
    private final ProductJPARepository jpaRepo;

    public ProductRepositoryAdapter(ProductJPARepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    @Override
    public List<Product> findByNameContaining(String name) {
        return jpaRepo.findByNameContainingIgnoreCase(name)
                .stream()
                .map(e -> new Product(e.getId(), e.getName(), e.getPrice(), e.getStock()))
                .toList();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return jpaRepo.findById(id)
                .map(e -> new Product(e.getId(), e.getName(), e.getPrice(), e.getStock()));
    }
}
