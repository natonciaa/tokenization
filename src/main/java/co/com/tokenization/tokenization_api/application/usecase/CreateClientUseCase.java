package co.com.tokenization.tokenization_api.application.usecase;

import co.com.tokenization.tokenization_api.domain.model.Card;
import co.com.tokenization.tokenization_api.domain.model.Client;
import co.com.tokenization.tokenization_api.domain.model.TokenRecord;
import co.com.tokenization.tokenization_api.domain.model.gateway.ClientRepository;
import co.com.tokenization.tokenization_api.domain.model.gateway.TokenRepository;
import co.com.tokenization.tokenization_api.infrastructure.config.CryptoUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

public class CreateClientUseCase {


    private ClientRepository clienteRepository;

    public Client create(String nombre, String email, String telefono, String direccion) {

        if (nombre == null || email == null || telefono == null || direccion == null)
            throw new IllegalArgumentException("Todos los campos son requeridos");
        Client client = new Client(null, nombre, email, telefono, direccion);
        return clienteRepository.save(client);
    }

    @Service
    public static class TokenizeCardUseCase {

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
            String payload = card.getNumber() + "|" + card.getCvv() + "|" + card.getExpiry() + "|" + card.getHolderName();
            String encrypted = crypto.encrypt(payload);
            String token = UUID.randomUUID().toString();
            TokenRecord rec = new TokenRecord(UUID.randomUUID(), token, encrypted, Instant.now());
            return repo.save(rec);
        }
    }
}
