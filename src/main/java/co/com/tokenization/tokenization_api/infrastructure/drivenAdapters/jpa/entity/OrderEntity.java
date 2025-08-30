package co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data @AllArgsConstructor @NoArgsConstructor
public class OrderEntity {
    private Long id;
    private String status;
    private String clientEmail;
    private double amount;
    private Instant createdAt;
    private String token; // token used (if any)
}
