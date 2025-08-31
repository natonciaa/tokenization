package co.com.tokenization.tokenization_api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchLog {
    private UUID id;
    private String productName;
    private Instant createdAt;
}
