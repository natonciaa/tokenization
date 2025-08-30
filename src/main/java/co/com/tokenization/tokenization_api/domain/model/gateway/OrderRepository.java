package co.com.tokenization.tokenization_api.domain.model.gateway;


import co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.entity.OrderEntity;

import java.util.Optional;

public interface OrderRepository {
    OrderEntity save(OrderEntity order);
    Optional<OrderEntity> findById(Long id);
}
