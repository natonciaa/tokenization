package co.com.tokenization.tokenization_api.application.usecase;

import co.com.tokenization.tokenization_api.domain.model.Card;
import co.com.tokenization.tokenization_api.domain.model.TokenRecord;
import co.com.tokenization.tokenization_api.domain.model.gateway.TokenRepository;
import co.com.tokenization.tokenization_api.infrastructure.config.CryptoUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenizeCardUseCaseTest {

    @Mock
    TokenRepository tokenRepo;
    @Mock
    CryptoUtils crypto;

    private TokenizeCardUseCase useCase;

    @BeforeEach
    void setup() {
        useCase = new TokenizeCardUseCase(tokenRepo, crypto, 0.0);
    }

    @Test
    void shouldThrowExceptionIfCardNumberInvalid() {
        Card card = new Card("123", "123", "12/25", "John Doe");

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(card));
    }

    @Test
    void shouldRejectTokenizationByPolicy() {
        Card card = new Card("4111111111111111", "123", "12/25", "John Doe");

        // Creamos un caso con probabilidad = 1.0 para forzar rechazo
        TokenizeCardUseCase rejectingUseCase = new TokenizeCardUseCase(tokenRepo, crypto, 1.0);

        assertThrows(RuntimeException.class, () -> rejectingUseCase.execute(card));
    }

    @Test
    void shouldTokenizeSuccessfully() {
        Card card = new Card("4111111111111111", "123", "12/25", "John Doe");
        String encrypted = "encryptedData";

        when(crypto.encrypt(anyString())).thenReturn(encrypted);
        when(tokenRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        TokenRecord result = useCase.execute(card);

        assertNotNull(result);
        assertNotNull(result.getToken());
        assertEquals(encrypted, result.getEncryptedCard());
        assertTrue(result.getCreatedAt().isBefore(LocalDateTime.now().plusSeconds(1)));

        verify(crypto).encrypt(contains("4111111111111111|123|12/25"));
        verify(tokenRepo).save(any(TokenRecord.class));
    }
}
