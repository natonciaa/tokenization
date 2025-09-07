package co.com.tokenization.tokenization_api.infrastructure.entryPoints;

import co.com.tokenization.tokenization_api.application.usecase.ProductUseCase;
import co.com.tokenization.tokenization_api.domain.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    ProductUseCase useCase;

    @InjectMocks
    ProductController controller;

    @Test
    void shouldReturnProducts() {
        Product p1 = new Product(1L, "Laptop", 10, 120);
        when(useCase.search("Lap")).thenReturn(List.of(p1));

        ResponseEntity<List<Product>> response = controller.search("Lap");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }
}

