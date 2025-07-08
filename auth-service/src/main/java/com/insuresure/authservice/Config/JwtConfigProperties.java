
package com.insuresure.authservice.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtConfigProperties {
    private long accessTokenExpiry;
    private long refreshTokenExpiry;
    private long emailVerificationExpiry;
    private long passwordResetExpiry;
    private String secretKey;
    private long validityTime;
    private String issuer;
}
