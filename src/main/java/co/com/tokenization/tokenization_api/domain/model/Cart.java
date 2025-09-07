package co.com.tokenization.tokenization_api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    private Long id;
    private List<CartItem> items = new ArrayList<>();
    private Status status = Status.PENDING;
    private double totalCost;

    public enum Status { PENDING, PAID, CANCELED }

    public void addItem(Product product, int quantity) {
        items.add(new CartItem(null, this.id, product, quantity));
        totalCost += product.getPrice() * quantity;
    }
}
