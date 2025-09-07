package co.com.tokenization.tokenization_api.application.usecase;

import co.com.tokenization.tokenization_api.domain.model.gateway.CartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateCartUseCaseTest {

    @Mock
    CartRepository cartRepository;

    @InjectMocks
    CreateCartUseCase useCase;

    @Test
    void shouldCreateCart() {
        Cart cart = new Cart(1L, Collections.emptyList(), Cart.Status.PENDING, 1000);
        when(cartRepository.save(any())).thenReturn(cart);

        Cart result = useCase.create(cart);
        assertNotNull(result);
        assertEquals(Cart.Status.PENDING, result.getStatus());
        verify(cartRepository).save(any());
    }
}
