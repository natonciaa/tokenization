package co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "token_records")
@Data
public class TokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private String encryptedCard;
    private LocalDateTime createdAt;
}
