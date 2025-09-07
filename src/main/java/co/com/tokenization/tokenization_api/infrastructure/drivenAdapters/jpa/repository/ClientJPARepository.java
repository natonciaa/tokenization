package co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.repository;

import co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;
@Repository
public interface ClientJPARepository extends JpaRepository<ClientEntity, Long> {
    ClientEntity findByEmail(String email);
    Optional<ClientEntity> findByPhone(String phone);
}

