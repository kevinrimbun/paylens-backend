package net.backend.paylens.config.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    @Value("${jwt.secret.key}")
    private String JWT_SECRET_KEY;

    @Value("${jwt.expiration.ms}")
    private Long JWT_EXPIRATION_MS;
}
