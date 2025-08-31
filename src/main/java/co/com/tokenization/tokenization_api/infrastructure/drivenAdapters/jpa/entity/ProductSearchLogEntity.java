package co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "product_search_logs")
@Data
public class ProductSearchLogEntity {
    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;
    private String productName;
    private Instant createdAt;
}
