package co.com.tokenization.tokenization_api.domain.model.gateway;

import co.com.tokenization.tokenization_api.domain.model.Order;
import co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.entity.OrderEntity;
import org.hibernate.grammars.ordering.OrderingParser;

public interface OrderRepository {
    Order save(Order order);
    Order findById(Long id);
}
