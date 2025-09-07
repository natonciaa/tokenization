package co.com.tokenization.tokenization_api.infrastructure.entryPoints;

import co.com.tokenization.tokenization_api.application.usecase.AddProductToCartUseCase;
import co.com.tokenization.tokenization_api.application.usecase.CreateCartUseCase;
import co.com.tokenization.tokenization_api.domain.model.Cart;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CreateCartUseCase createCart;
    private final AddProductToCartUseCase addProduct;

    public CartController(CreateCartUseCase createCart, AddProductToCartUseCase addProduct) {
        this.createCart = createCart;
        this.addProduct = addProduct;
    }
    @PostMapping
    public Cart createCart(@RequestBody Cart cart) {
        return createCart.create(cart);
    }

    @PostMapping("/{cartId}/product/{productId}/{quantity}")
    public Cart addProduct(@PathVariable String cartId, @PathVariable String productId, @PathVariable int quantity) {
        return addProduct.addProduct(Long.parseLong(cartId), Long.parseLong(productId), quantity);
    }
}
