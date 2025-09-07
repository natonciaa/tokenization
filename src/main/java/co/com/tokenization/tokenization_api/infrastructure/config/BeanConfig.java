package co.com.tokenization.tokenization_api.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
            byte[] b = new byte[32];
            new java.security.SecureRandom().nextBytes(b);
            key = java.util.Base64.getEncoder().encodeToString(b);
        }
        return new CryptoUtils(key);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new Hibernate6Module());

        return objectMapper;
    }
}
