package co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.adapters;

import co.com.tokenization.tokenization_api.domain.model.ProductSearchLog;
import co.com.tokenization.tokenization_api.domain.model.gateway.ProductSearchLogRepository;
import co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.entity.ProductSearchLogEntity;
import co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.repository.ProductSearchLogJPARepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ProductSearchLogRepositoryAdapter implements ProductSearchLogRepository {

    private final ProductSearchLogJPARepository springRepo;

    @Override
    public ProductSearchLog save(ProductSearchLog log) {
        ProductSearchLogEntity e = new ProductSearchLogEntity();
        e.setId(log.getId() == null ? UUID.randomUUID() : log.getId());
        e.setProductName(log.getProductName());
        e.setCreatedAt(log.getCreatedAt() == null ? Instant.now() : log.getCreatedAt());
        ProductSearchLogEntity saved = springRepo.save(e);
        return new ProductSearchLog(saved.getId(), saved.getProductName(), saved.getCreatedAt());
    }
}

