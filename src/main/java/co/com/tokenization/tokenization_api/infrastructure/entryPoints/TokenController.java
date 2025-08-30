package co.com.tokenization.tokenization_api.infrastructure.entryPoints;

import co.com.tokenization.tokenization_api.application.usecase.TokenizeCardUseCase;
import co.com.tokenization.tokenization_api.domain.model.Card;
import co.com.tokenization.tokenization_api.domain.model.TokenRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class TokenController {

    private final TokenizeCardUseCase useCase;

    public TokenController(TokenizeCardUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }

    @PostMapping("/tokenize")
    public ResponseEntity<?> tokenize(@RequestBody Card card) {
        try {
            TokenRecord r = useCase.execute(card);
            return ResponseEntity.status(201).body(r);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(412).body(e.getMessage());
        }
    }
}
