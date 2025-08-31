package co.com.tokenization.tokenization_api.application.usecase;

import co.com.tokenization.tokenization_api.domain.model.Product;
import co.com.tokenization.tokenization_api.domain.model.ProductSearchLog;
import co.com.tokenization.tokenization_api.domain.model.gateway.ProductRepository;
import co.com.tokenization.tokenization_api.domain.model.gateway.ProductSearchLogRepository;
import co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.repository.ProductSearchLogJPARepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductUseCase {

    private final ProductRepository repo;
    private final ProductSearchLogRepository productSearchLogRepository;
    private final int minStock;

    public ProductUseCase(ProductRepository repo, ProductSearchLogRepository productSearchLog, @Value("${app.product.min-stock-display:1}") int minStock) {
        this.repo = repo;
        this.minStock = minStock;
        this.productSearchLogRepository = productSearchLog;
    }

    public List<Product> search(String productName) {
        logSearchAsync(productName);
        List<Product> results = repo.findByNameContaining(productName);
        return results.stream()
                .filter(p -> p.getStock() >= minStock)
                .collect(Collectors.toList());
    }

    @Async
    public void logSearchAsync(String productName) {
        productSearchLogRepository.save(new ProductSearchLog(null, productName, Instant.now()));
    }

}


