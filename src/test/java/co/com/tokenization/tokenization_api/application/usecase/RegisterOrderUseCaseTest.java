package co.com.tokenization.tokenization_api.application.usecase;

import co.com.tokenization.tokenization_api.domain.model.Client;
import co.com.tokenization.tokenization_api.domain.model.gateway.CartRepository;
import co.com.tokenization.tokenization_api.domain.model.gateway.ClientRepository;
import co.com.tokenization.tokenization_api.domain.model.gateway.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterOrderUseCaseTest {

    @Mock
    CartRepository cartRepo;
    @Mock
    OrderRepository orderRepo;
    @Mock
    ClientRepository clientRepo;

    @InjectMocks
    RegisterOrderUseCase useCase;

    @Test
    void shouldRegisterOrderSuccessfully() {
        Client client = new Client(1L, "Alice", "alice@mail.com", "123", "Address");
        Cart cart = new Cart(1L, new ArrayList<>(), Cart.Status.PENDING,1000);

        when(clientRepo.findByEmail("alice@mail.com")).thenReturn(client);
        when(cartRepo.findById(1L)).thenReturn(cart);
        when(orderRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Order result = useCase.register(1L, client, "tok123", "Delivery street");
        assertEquals("tok123", result.getCreditCardToken());
        assertEquals("Delivery street", result.getDeliveryAddress());
        assertFalse(result.isPaid());
        verify(orderRepo).save(any());
    }

    @Test
    void shouldFailIfClientNotFound() {
        Client client = new Client(1L, "Alice", "alice@mail.com", "123", "Address");
        when(clientRepo.findByEmail("alice@mail.com")).thenReturn(null);

        assertThrows(IllegalArgumentException.class,
                () -> useCase.register(1L, client, "tok123", "Delivery street"));
    }

    @Test
    void shouldFailIfCartNotFound() {
        Client client = new Client(1L, "Alice", "alice@mail.com", "123", "Address");
        when(clientRepo.findByEmail("alice@mail.com")).thenReturn(client);
        when(cartRepo.findById(1L)).thenReturn(null);

        assertThrows(IllegalArgumentException.class,
                () -> useCase.register(1L, client, "tok123", "Delivery street"));
    }
}
