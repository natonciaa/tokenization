package co.com.tokenization.tokenization_api.application.usecase;

import co.com.tokenization.tokenization_api.domain.model.Cart;
import co.com.tokenization.tokenization_api.domain.model.Product;
import co.com.tokenization.tokenization_api.domain.model.gateway.CartRepository;
import co.com.tokenization.tokenization_api.domain.model.gateway.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class AddProductToCartUseCase {
    private final CartRepository cartRepo;
    private final ProductRepository productRepo;

    public AddProductToCartUseCase(CartRepository cartRepo, ProductRepository productRepo) {
        this.cartRepo = cartRepo;
        this.productRepo = productRepo;
    }

    public Cart addProduct(Long cartId, Long productId, int quantity) {
        Cart cart = cartRepo.findById(cartId);
        if(cart == null)
            new IllegalArgumentException("Cart not found");

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        cart.addItem(product, quantity);
        return cartRepo.save(cart);
    }
}
