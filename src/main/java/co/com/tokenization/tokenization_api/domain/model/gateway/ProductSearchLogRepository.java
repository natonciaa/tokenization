package co.com.tokenization.tokenization_api.domain.model.gateway;


import co.com.tokenization.tokenization_api.domain.model.ProductSearchLog;

public interface ProductSearchLogRepository {
    void save(ProductSearchLog log);
}
