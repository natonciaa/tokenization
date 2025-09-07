package co.com.tokenization.tokenization_api.infrastructure.entryPoints;

import co.com.tokenization.tokenization_api.application.usecase.EmailService;
import co.com.tokenization.tokenization_api.application.usecase.PaymentUseCase;
import co.com.tokenization.tokenization_api.application.usecase.RegisterOrderUseCase;
import co.com.tokenization.tokenization_api.domain.model.Cart;
import co.com.tokenization.tokenization_api.domain.model.Client;
import co.com.tokenization.tokenization_api.domain.model.Order;
import co.com.tokenization.tokenization_api.domain.model.TokenRecord;
import co.com.tokenization.tokenization_api.domain.model.gateway.CartRepository;
import co.com.tokenization.tokenization_api.domain.model.gateway.ClientRepository;
import co.com.tokenization.tokenization_api.domain.model.gateway.OrderRepository;
import co.com.tokenization.tokenization_api.domain.model.gateway.TokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    OrderRepository orderRepo;
    @Mock
    TokenRepository tokenRepo;
    @Mock
    CartRepository cartRepo;
    @Mock
    ClientRepository clientRepo;
    @Mock
    EmailService mailSender;

    @Mock
    RegisterOrderUseCase registerOrder;

    @Mock
    PaymentUseCase paymentUseCase;

    @InjectMocks
    OrderController controller;

    private Client client;
    private Cart cart;
    private Order order;
    private OrderController.OrderRequest orderRequest;
    private TokenRecord token;

    @BeforeEach
    void setup() {
        client = new Client(1L, "Alice", "alice@mail.com", "123", "Address");
        cart = new Cart(1L, new ArrayList<>(), Cart.Status.PENDING, 1000);
        order = new Order(1L, client, cart, "tok123", "Some street", true);
        token = new TokenRecord(1L, "tok123", "****1111", LocalDateTime.now());
        orderRequest = new OrderController.OrderRequest(client, "tok123", "Some street");
    }

    @Test
    void shouldCreateOrder() {
        Client client = new Client(1L, "Bob", "bob@test.com", "111", "Street 1");
        OrderController.OrderRequest request = new OrderController.OrderRequest(client, "token123", "Address");

        Order order = new Order();
        order.setId(1L);

        when(registerOrder.register(1L, client, "token123", "Address")).thenReturn(order);

        Order result = controller.createOrder(1L, request);

        assertEquals(1L, result.getId());
    }

    @Test
    void shouldPayOrder() {
        when(paymentUseCase.processPayment(order.getId(), order.getClient(), order.getCreditCardToken(), order.getDeliveryAddress())).thenReturn(order);
        Order result = controller.payOrder(1L, orderRequest);

        assertEquals(1L, result.getId());
    }
}
