package co.com.tokenization.tokenization_api.infrastructure.entryPoints;

import co.com.tokenization.tokenization_api.application.usecase.ProductUseCase;
import co.com.tokenization.tokenization_api.domain.model.Product;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductUseCase useCase;

    @GetMapping
    public ResponseEntity<List<Product>> search(@RequestParam(required=false, defaultValue="") String productName) {
        return ResponseEntity.ok(useCase.search(productName));
    }
}
