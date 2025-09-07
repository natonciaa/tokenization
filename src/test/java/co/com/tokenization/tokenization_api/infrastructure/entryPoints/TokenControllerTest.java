package co.com.tokenization.tokenization_api.infrastructure.entryPoints;

import co.com.tokenization.tokenization_api.application.usecase.TokenizeCardUseCase;
import co.com.tokenization.tokenization_api.domain.model.Card;
import co.com.tokenization.tokenization_api.domain.model.TokenRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokenControllerTest {

    @Mock
    TokenizeCardUseCase useCase;

    @InjectMocks
    TokenController controller;

    @Test
    void shouldReturnPong() {
        ResponseEntity<String> response = controller.ping();
        assertEquals("pong", response.getBody());
    }

    @Test
    void shouldTokenizeSuccessfully() {
        Card card = new Card("4111111111111111", "123", "12/25","John Doe");
        TokenRecord rec = new TokenRecord(1L, "t1", "enc", null);

        when(useCase.execute(card)).thenReturn(rec);

        ResponseEntity<?> response = controller.tokenize(card);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(rec, response.getBody());
    }

    @Test
    void shouldReturnBadRequestOnIllegalArgument() {
        Card card = new Card("411", "123", "12/25","John Doe");
        when(useCase.execute(card)).thenThrow(new IllegalArgumentException("Invalid card"));

        ResponseEntity<?> response = controller.tokenize(card);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Invalid card", response.getBody());
    }

    @Test
    void shouldReturnPreconditionFailedOnRuntimeException() {
        Card card = new Card("4111111111111111", "123", "12/25","John Doe");
        when(useCase.execute(card)).thenThrow(new RuntimeException("Rejected"));

        ResponseEntity<?> response = controller.tokenize(card);

        assertEquals(412, response.getStatusCodeValue());
        assertEquals("Rejected", response.getBody());
    }
}
