package co.com.tokenization.tokenization_api.domain.model.gateway;

import co.com.tokenization.tokenization_api.domain.model.Client;

import java.util.Optional;

public interface ClienteRepository {
    Client save(Client client);
    Optional<Client> findByEmail(String email);
    Optional<Client> findByPhone(String phone);


}
