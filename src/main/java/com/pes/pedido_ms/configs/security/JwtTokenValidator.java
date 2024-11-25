package com.pes.pedido_ms.configs.security;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

@Component
public class JwtTokenValidator {

    private final JWTVerifier verifier;

    @Value("${jwt.public-key}")
    private String publicKey;

    // public JwtTokenValidator() {
    //     Algorithm algorithm = Algorithm.HMAC256("jwt-key");
    //     this.verifier = JWT.require(algorithm).build();
    // }

    // public DecodedJWT validateToken(String token) {
    //     System.out.println(token);
    //     return verifier.verify(token);
    // }

    public JwtTokenValidator(@Value("${jwt.public-key}") String publicKey) {
        RSAPublicKey rsaPublicKey = getPublicKeyFromPem(publicKey);
        Algorithm algorithm = Algorithm.RSA256(rsaPublicKey, null);
        this.verifier = JWT.require(algorithm).build();
    }

    public DecodedJWT validateToken(String token) throws NoSuchAlgorithmException {
        System.out.println("Validando token: " + token);
        return verifier.verify(token);
    }

    private RSAPublicKey getPublicKeyFromPem(String publicKey) {
        try {
            
            String publicKeyContent = publicKey.replaceAll("\\s+", "");

            String a = publicKeyContent.replaceAll("\\s+", "");
            byte[] keyBytes = Base64.getDecoder().decode(a);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(keyBytes));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar chave pública", e);
        }
    }
}

