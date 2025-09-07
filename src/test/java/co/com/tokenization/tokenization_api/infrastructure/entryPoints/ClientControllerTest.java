package co.com.tokenization.tokenization_api.infrastructure.entryPoints;

import co.com.tokenization.tokenization_api.application.usecase.CreateClientUseCase;
import co.com.tokenization.tokenization_api.domain.model.Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    @Mock
    CreateClientUseCase useCase;

    @InjectMocks
    ClientController controller;

    @Test
    void shouldCreateClientSuccessfully() {
        Client client = new Client(1L, "Alice", "alice@test.com", "123", "Street 123");
        when(useCase.save(client)).thenReturn(client);

        ResponseEntity<?> response = controller.create(client);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(client, response.getBody());
    }

    @Test
    void shouldReturnBadRequestIfInvalid() {
        Client client = new Client(1L, "Alice", "alice@test.com", "123", "Street 123");
        when(useCase.save(client)).thenThrow(new IllegalArgumentException("Invalid"));

        ResponseEntity<?> response = controller.create(client);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Invalid", response.getBody());
    }
}
