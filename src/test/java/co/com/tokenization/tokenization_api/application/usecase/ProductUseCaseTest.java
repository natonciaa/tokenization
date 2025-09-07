package co.com.tokenization.tokenization_api.application.usecase;

import co.com.tokenization.tokenization_api.domain.model.gateway.ProductRepository;
import co.com.tokenization.tokenization_api.domain.model.gateway.ProductSearchLogRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ProductUseCaseTest {

    @Mock
    ProductRepository productRepo;

    @Mock
    ProductSearchLogRepository searchLogRepo;

    @InjectMocks
    ProductUseCase useCase = new ProductUseCase(productRepo, searchLogRepo, 5);

//    @Test
//    void shouldReturnProductsWithEnoughStock() {
//        Product p1 = new Product("1", "Laptop", 10, 120);
//        Product p2 = new Product("2", "Phone", 3, 80);
//        when(productRepo.findByNameContaining("Lap")).thenReturn(List.of(p1, p2));
//
//        List<Product> result = useCase.search("Lap");
//
//        assertEquals(1, result.size());
//        assertEquals("Laptop", result.get(0).getName());
//        verify(searchLogRepo, atLeastOnce()).save(any(ProductSearchLog.class));
//    }
}
