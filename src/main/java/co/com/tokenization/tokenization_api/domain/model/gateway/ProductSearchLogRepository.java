package co.com.tokenization.tokenization_api.domain.model.gateway;


import co.com.tokenization.tokenization_api.domain.model.ProductSearchLog;

public interface ProductSearchLogRepository {
    ProductSearchLog save(ProductSearchLog log);
}
