package co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.adapters;

import co.com.tokenization.tokenization_api.domain.model.TokenRecord;
import co.com.tokenization.tokenization_api.domain.model.gateway.TokenRepository;
import co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.entity.TokenEntity;
import co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.repository.TokenJPARepository;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Component

public class TokenRepositoryAdapter implements TokenRepository {

    private final TokenJPARepository tokenJPARepository;

    public TokenRepositoryAdapter(TokenJPARepository tokenJPARepository) {
        this.tokenJPARepository = tokenJPARepository;
    }

    @Override
    public TokenRecord save(TokenRecord record) {
        TokenEntity e = new TokenEntity();
        e.setToken(record.getToken());
        e.setEncryptedCard(record.getEncryptedCard());
        e.setCreatedAt(record.getCreatedAt() == null ? LocalDateTime.now() : record.getCreatedAt());
        TokenEntity saved = tokenJPARepository.save(e);
        return new TokenRecord(saved.getId(), saved.getToken(), saved.getEncryptedCard(), saved.getCreatedAt());
    }

    @Override
    public TokenRecord findByToken(String token) {
        TokenEntity e = tokenJPARepository.findByToken(token);
        if (e == null) return null;
        return new TokenRecord(e.getId(), e.getToken(), e.getEncryptedCard(), e.getCreatedAt());
    }
}
