package co.com.tokenization.tokenization_api.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableAsync;

@ComponentScan(basePackages = "co.com.tokenization.tokenization_api.application.usecase",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")
        },
        useDefaultFilters = false)
@Configuration
@EnableAsync
public class BeanConfig {

    @Bean
    public CryptoUtils cryptoUtils(@Value("${APP_CRYPTO_KEY:}") String envKey,
                                   @Value("${app.crypto.key:}") String configKey) {
        String key = (envKey != null && !envKey.isBlank()) ? envKey : configKey;
        if (key == null || key.isBlank()) {
            // For development create a default key (NOT for prod)
            byte[] b = new byte[32];
            new java.security.SecureRandom().nextBytes(b);
            key = java.util.Base64.getEncoder().encodeToString(b);
        }
        return new CryptoUtils(key);
    }
}
