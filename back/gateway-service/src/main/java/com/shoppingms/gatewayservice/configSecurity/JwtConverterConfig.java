package com.shoppingms.gatewayservice.configSecurity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ConfigurationProperties(prefix = "jwt.auth.converter")
@Configuration
public class JwtConverterConfig {
    private String resourceId;
    private String personalAttribute;
}
