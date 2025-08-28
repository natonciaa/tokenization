package co.com.tokenization.tokenization_api.domain.usecase;

import co.com.tokenization.tokenization_api.domain.model.Client;
import co.com.tokenization.tokenization_api.domain.model.gateway.ClienteRepository;

public class CreateClienteUseCase {


    private ClienteRepository clienteRepository;

    public Client create(String nombre, String email, String telefono, String direccion) {

        if (nombre == null || email == null || telefono == null || direccion == null)
            throw new IllegalArgumentException("Todos los campos son requeridos");
        Client client = new Client(null, nombre, email, telefono, direccion);
        return clienteRepository.save(client);
    }
}
