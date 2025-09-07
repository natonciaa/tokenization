package co.com.tokenization.tokenization_api.application.usecase;

import co.com.tokenization.tokenization_api.domain.model.Cart;
import co.com.tokenization.tokenization_api.domain.model.gateway.CartRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateCartUseCase {
    private final CartRepository cartRepo;

    public CreateCartUseCase(CartRepository cartRepo) {
        this.cartRepo = cartRepo;
    }

    public Cart create(Cart cart) {
        cart.setId(null);
        cart.setStatus(Cart.Status.PENDING);
        return cartRepo.save(cart);
    }
}
