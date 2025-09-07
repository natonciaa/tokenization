package co.com.tokenization.tokenization_api.application.usecase;

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
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentUseCaseTest {

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

    @InjectMocks
    PaymentUseCase useCase;

    private Client client;
    private Cart cart;
    private Order order;
    private TokenRecord token;

    @BeforeEach
    void setup() {
        client = new Client(1L, "Alice", "alice@mail.com", "123", "Address");
        cart = new Cart(1L, new ArrayList<>(), Cart.Status.PENDING,1000);
        order = new Order(1L, client, cart,"tok123", "Some street", true);
        token = new TokenRecord(1L, "tok123", "****1111", LocalDateTime.now() );
    }

    @Test
    void shouldPaySuccessfully() {
        ReflectionTestUtils.setField(useCase, "successProbability", 0.75);
        ReflectionTestUtils.setField(useCase, "maxRetries", 2);

        when(tokenRepo.findByToken("tok123")).thenReturn(token);
        when(orderRepo.findById(1L)).thenReturn(order);
        when(cartRepo.save(any())).thenReturn(cart);
        when(orderRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(cartRepo.findById(order.getCart().getId())).thenReturn(cart);
        when(clientRepo.findByEmail(client.getEmail())).thenReturn(client);

        Order result = useCase.processPayment(order.getId(),order.getClient(), order.getCreditCardToken(), order.getDeliveryAddress());

        assertTrue(result.isPaid());
        assertEquals(Cart.Status.PAID, result.getCart().getStatus());
        verify(mailSender).sendSimpleMessage(eq("alice@mail.com"), contains("success"), anyString());
    }

    @Test
    void shouldFailIfTokenNotFound() {
        when(orderRepo.findById(1L)).thenReturn(order);

        when(tokenRepo.findByToken("tok123")).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> useCase.processPayment(order.getId(),order.getClient(), order.getCreditCardToken(), order.getDeliveryAddress()));
        verify(mailSender).sendSimpleMessage(eq("alice@mail.com"), contains("failed"), anyString());
    }

    @Test
    void shouldFailIfOrderNotFound() {
//        when(tokenRepo.findByToken("tok123")).thenReturn(token);
        when(orderRepo.findById(1L)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> useCase.processPayment(order.getId(),order.getClient(), order.getCreditCardToken(), order.getDeliveryAddress()));
    }

    @Test
    void shouldFailAfterRetries() {
        order = new Order(1L, client, cart,"tok123", "Some street", false);

        when(tokenRepo.findByToken("tok123")).thenReturn(token);
        when(orderRepo.findById(1L)).thenReturn(order);
        when(orderRepo.save(any())).thenReturn(order);
        when(cartRepo.findById(order.getCart().getId())).thenReturn(cart);
        when(cartRepo.save(any())).thenReturn(cart);
        when(clientRepo.findByEmail(client.getEmail())).thenReturn(client);
        order = new Order(1L, client, cart,"tok123", "Some street", true);

        Order result = useCase.processPayment(order.getCart().getId(), order.getClient(), order.getCreditCardToken(), order.getDeliveryAddress());

        assertFalse(result.isPaid());
        assertEquals(Cart.Status.CANCELED, result.getCart().getStatus());
        verify(mailSender).sendSimpleMessage(eq("alice@mail.com"), contains("Payment failed"), contains("Your payment failed after 0 attempts"));
    }
}
