package com.pes.pedido_ms.configs.feignclient;

import org.springframework.stereotype.Component;

import com.pes.pedido_ms.configs.security.JwtUtil;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

    private final JwtUtil jwtUtil;

    public FeignClientInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String token = jwtUtil.token();
        if (token != null) {
            requestTemplate.header("Authorization", "Bearer " + token);
        }
    }
}
