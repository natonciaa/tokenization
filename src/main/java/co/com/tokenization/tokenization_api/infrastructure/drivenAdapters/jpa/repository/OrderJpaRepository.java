package co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.repository;

import co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findByCreditCardToken(String id);
}
