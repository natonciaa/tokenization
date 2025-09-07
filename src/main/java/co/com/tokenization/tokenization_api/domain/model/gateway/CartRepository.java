package co.com.tokenization.tokenization_api.domain.model.gateway;


import co.com.tokenization.tokenization_api.domain.model.Cart;

import java.util.Optional;

public interface CartRepository {
    Cart save(Cart cart);
    Cart findById(Long id);
}
