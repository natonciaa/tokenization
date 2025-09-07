package co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "product_search_logs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private LocalDateTime createdAt;
}
