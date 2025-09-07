package co.com.tokenization.tokenization_api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    private Long id;
    private Long cartId;
    private Product product;
    private int quantity;
}
