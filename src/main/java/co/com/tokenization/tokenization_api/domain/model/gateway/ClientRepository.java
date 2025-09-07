package co.com.tokenization.tokenization_api.domain.model.gateway;

import co.com.tokenization.tokenization_api.domain.model.Client;

import java.util.Optional;

public interface ClientRepository {
    Client save(Client client);
    Client findByEmail(String email);
    Optional<Client> findByPhone(String phone);


}
