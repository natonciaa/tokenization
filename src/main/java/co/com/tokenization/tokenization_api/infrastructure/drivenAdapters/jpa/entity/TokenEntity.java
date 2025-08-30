package co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "token_records")
@Data
public class TokenEntity {
    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;
    private String token;
    @Lob
    private String encryptedCard;
    private Instant createdAt;
}
