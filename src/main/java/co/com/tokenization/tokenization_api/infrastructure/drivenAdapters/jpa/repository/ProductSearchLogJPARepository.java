package co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.repository;

import co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.entity.ProductSearchLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ProductSearchLogJPARepository extends JpaRepository<ProductSearchLogEntity, UUID> {


}
