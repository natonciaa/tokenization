package co.com.tokenization.tokenization_api.application.usecase;

import co.com.tokenization.tokenization_api.domain.model.Product;
import co.com.tokenization.tokenization_api.domain.model.ProductSearchLog;
import co.com.tokenization.tokenization_api.domain.model.gateway.ProductRepository;
import co.com.tokenization.tokenization_api.domain.model.gateway.ProductSearchLogRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductUseCase {
    private final ProductRepository productRepo;
    private final ProductSearchLogRepository logRepo;
    private final int minStock;

    public ProductUseCase(ProductRepository productRepo,
                          ProductSearchLogRepository logRepo,
                          @Value("${app.product.min-stock-display:1}") int minStock) {
        this.productRepo = productRepo;
        this.logRepo = logRepo;
        this.minStock = minStock;
    }

    public List<Product> search(String name) {
        logSearchAsync(name);
        return productRepo.findByNameContaining(name).stream()
                .filter(p -> p.getStock() >= minStock)
                .collect(Collectors.toList());
    }

    @Async
    public void logSearchAsync(String term) {
        logRepo.save(new ProductSearchLog(null, term, LocalDateTime.now()));
    }
}
