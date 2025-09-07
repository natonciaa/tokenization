package co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.adapters;

import co.com.tokenization.tokenization_api.domain.model.Order;
import co.com.tokenization.tokenization_api.domain.model.gateway.OrderRepository;
import co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.entity.OrderEntity;
import co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.repository.OrderJpaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepository {

    private final OrderJpaRepository jpaRepository;

    private final OrderEntityMapper mapper = new OrderEntityMapper();

    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = mapper.toEntity(order);

        return mapper.toDomain(jpaRepository.save(orderEntity));
    }

    @Override
    public Order findById(Long id) {
        Optional<OrderEntity> entity = jpaRepository.findById(id);
        return entity.map(mapper::toDomain).orElse(null);
    }

}
