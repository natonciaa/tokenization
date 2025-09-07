package co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.repository;

import co.com.tokenization.tokenization_api.infrastructure.drivenAdapters.jpa.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartJPARepository extends JpaRepository<CartEntity, Long> {}