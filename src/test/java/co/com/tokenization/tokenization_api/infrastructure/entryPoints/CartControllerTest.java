package co.com.tokenization.tokenization_api.infrastructure.entryPoints;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {

    @Mock
    CreateCartUseCase createCart;

    @Mock
    AddProductToCartUseCase addProduct;

    @InjectMocks
    CartController controller;

    @Test
    void shouldCreateCart() {
        Cart cart = new Cart(1L, new ArrayList<>(), Cart.Status.PENDING,1000);
        when(createCart.create(cart)).thenReturn(cart);

        Cart result = controller.createCart(cart);

        assertEquals(1L, result.getId());
    }

    @Test
    void shouldAddProductToCart() {
        Cart cart = new Cart(1L, new ArrayList<>(), Cart.Status.PENDING,1000);
        Product p = new Product(1L, "Laptop", 5, 10);
        CartItem item = new CartItem(p, 1);
        List<CartItem> products = new ArrayList<>();
        products.add(item);
        cart.setItems(products);

        when(addProduct.addProduct(1L, 1L,1)).thenReturn(cart);

        Cart result = controller.addProduct(String.valueOf(1L), String.valueOf(1L),1);

        assertEquals(1, result.getItems().size());
    }
}
