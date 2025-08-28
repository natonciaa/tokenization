package co.com.tokenization.tokenization_api.domain.model.gateway;


import co.com.tokenization.tokenization_api.domain.model.TokenRecord;

public interface TokenRepository {
    TokenRecord save(TokenRecord record);

    TokenRecord findByToken(String token);
}
