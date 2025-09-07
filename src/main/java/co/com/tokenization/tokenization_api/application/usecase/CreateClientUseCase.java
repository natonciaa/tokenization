package co.com.tokenization.tokenization_api.application.usecase;

import co.com.tokenization.tokenization_api.domain.model.Client;
import co.com.tokenization.tokenization_api.domain.model.gateway.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateClientUseCase {
    private final ClientRepository repo;

    public CreateClientUseCase(ClientRepository repo) {
        this.repo = repo;
    }

    public Client save(Client client) {
        if (repo.findByEmail(client.getEmail()) != null)
            throw new IllegalArgumentException("Email exists");
        repo.findByPhone(client.getPhone()).ifPresent(c -> {
            throw new IllegalArgumentException("Phone exists");
        });
        return repo.save(client);
    }
}
