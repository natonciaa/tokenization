package co.com.tokenization.tokenization_api.domain.model.gateway;

import co.com.tokenization.tokenization_api.domain.model.CartItem;

public interface CartItemRepository {
    CartItem save(CartItem item);
}