package co.com.tokenization.tokenization_api.application.usecase;

import co.com.tokenization.tokenization_api.domain.model.Cart;
import co.com.tokenization.tokenization_api.domain.model.Client;
import co.com.tokenization.tokenization_api.domain.model.Order;

import co.com.tokenization.tokenization_api.domain.model.gateway.CartRepository;
import co.com.tokenization.tokenization_api.domain.model.gateway.ClientRepository;
import co.com.tokenization.tokenization_api.domain.model.gateway.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegisterOrderUseCase {
    private final CartRepository cartRepo;
    private final OrderRepository orderRepo;
    private final ClientRepository clientRepo;

    public RegisterOrderUseCase(CartRepository cartRepo, OrderRepository orderRepo, ClientRepository clientRepo) {
        this.cartRepo = cartRepo;
        this.orderRepo = orderRepo;
        this.clientRepo = clientRepo;
    }

    public Order register(Long cartId, Client client, String cardToken, String deliveryAddress) {
        Cart cart = cartRepo.findById(cartId);
        if(cart == null)
             throw new IllegalArgumentException("Cart not found");
        Client clientFound = clientRepo.findByEmail(client.getEmail());
        if (clientFound == null) {
            throw new IllegalArgumentException("Client not found");
        }

        Order order = new Order();
        order.setId(null); // Autogenerado por DB
        order.setClient(clientFound);
        order.setCreditCardToken(cardToken);
        order.setDeliveryAddress(deliveryAddress);
        order.setCart(cart);
        order.setPaid(false);

        return orderRepo.save(order);
    }
}
