package co.com.tokenization.tokenization_api.application.usecase;

import co.com.tokenization.tokenization_api.domain.model.Client;
import co.com.tokenization.tokenization_api.domain.model.gateway.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateClientUseCaseTest {

    @Mock
    ClientRepository clientRepository;

    @InjectMocks
    CreateClientUseCase useCase;

    @Test
    void shouldCreateClient() {
        Client client = new Client(1L, "Alice", "alice@mail.com", "123456", "address");

        when(clientRepository.save(any())).thenReturn(client);
        useCase.save(client);
        verify(clientRepository).save(any());
    }

    @Test
    void shouldFailOnDuplicateEmail() {
        Client client = new Client(1L, "Alice", "alice@mail.com", "123456","address");

        when(clientRepository.findByEmail("alice@mail.com")).thenReturn(client);
        assertThrows(IllegalArgumentException.class, () -> useCase.save(client));
    }

    @Test
    void shouldFailOnDuplicatePhoneNumber() {
        Client client = new Client(1L, "Alice", "alice@mail.com", "123456","address");
        when(clientRepository.findByPhone("123456")).thenReturn(Optional.of(client));
        assertThrows(IllegalArgumentException.class, () -> useCase.save(client));
    }
}

