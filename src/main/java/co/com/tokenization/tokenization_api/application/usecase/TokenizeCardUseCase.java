package co.com.tokenization.tokenization_api.application.usecase;

import co.com.tokenization.tokenization_api.domain.model.Card;
import co.com.tokenization.tokenization_api.domain.model.TokenRecord;
import co.com.tokenization.tokenization_api.domain.model.gateway.TokenRepository;
import co.com.tokenization.tokenization_api.infrastructure.config.CryptoUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TokenizeCardUseCase {

    private final TokenRepository repo;
    private final CryptoUtils crypto;
    private final double rejectProbability;

    public TokenizeCardUseCase(TokenRepository repo, CryptoUtils crypto,
                               @Value("${app.tokenization.reject-probability}") double rejectProbability) {
        this.repo = repo;
        this.crypto = crypto;
        this.rejectProbability = rejectProbability;
    }

    public TokenRecord execute(Card card) {
        if (card.getNumber() == null || card.getNumber().length() < 12) {
            throw new IllegalArgumentException("Card number invalid");
        }
        double r = Math.random();
        if (r < rejectProbability) {
            throw new RuntimeException("Tokenization rejected by policy");
        }

        String encrypted = crypto.encrypt(card.getNumber() + "|" + card.getCvv() + "|" + card.getExpiry());
        String token = UUID.randomUUID().toString();

        TokenRecord rec = new TokenRecord(null, token, encrypted, LocalDateTime.now());
        return repo.save(rec);
    }
}
