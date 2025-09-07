package co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "orders")
@Data
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity client;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private CartEntity cart;

    @Column(nullable = false)
    private String creditCardToken;

    @Column(nullable = false)
    private String deliveryAddress;

    private boolean paid;
}
