package co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.adapters;

import co.com.tokenization.tokenization_api.domain.model.ProductSearchLog;
import co.com.tokenization.tokenization_api.domain.model.gateway.ProductSearchLogRepository;
import co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.entity.ProductSearchLogEntity;
import co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.repository.ProductSearchLogJPARepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ProductSearchLogRepositoryAdapter implements ProductSearchLogRepository {

    private final ProductSearchLogJPARepository jpaRepo;

    @Override
    public void save(ProductSearchLog log) {
        ProductSearchLogEntity entity = new ProductSearchLogEntity(
                log.getId(),
                log.getProductName(),
                log.getCreatedAt()
        );
        jpaRepo.save(entity);
    }
}

