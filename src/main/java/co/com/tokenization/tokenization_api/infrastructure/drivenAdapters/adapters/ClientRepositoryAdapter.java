package co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.adapters;

import co.com.tokenization.tokenization_api.domain.model.Client;
import co.com.tokenization.tokenization_api.domain.model.gateway.ClientRepository;
import co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.entity.ClientEntity;
import co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.repository.ClientJPARepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ClientRepositoryAdapter implements ClientRepository {

    private final ClientJPARepository spring;

    public ClientRepositoryAdapter(ClientJPARepository spring) {
        this.spring = spring;
    }

    @Override
    public Client save(Client client) {
        ClientEntity e = new ClientEntity();
        e.setId(client.getId());
        e.setName(client.getName());
        e.setEmail(client.getEmail());
        e.setPhone(client.getPhone());
        e.setAddress(client.getAddress());
        ClientEntity saved = spring.save(e);
        return new Client(saved.getId(), saved.getName(), saved.getEmail(), saved.getPhone(), saved.getAddress());
    }

    @Override
    public Optional<Client> findByEmail(String email) {
        return spring.findByEmail(email).map(e -> new Client(e.getId(), e.getName(), e.getEmail(), e.getPhone(), e.getAddress()));
    }

    @Override
    public Optional<Client> findByPhone(String phone) {
        return spring.findByPhone(phone).map(e -> new Client(e.getId(), e.getName(), e.getEmail(), e.getPhone(), e.getAddress()));
    }
}
