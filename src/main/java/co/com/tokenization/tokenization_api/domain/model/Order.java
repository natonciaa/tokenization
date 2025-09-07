package co.com.tokenization.tokenization_api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Long id;
    private Client client;
    private Cart cart;
    private String creditCardToken;
    private String deliveryAddress;
    private boolean paid;
}
