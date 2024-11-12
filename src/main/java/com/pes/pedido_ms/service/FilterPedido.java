package com.pes.pedido_ms.service;

import lombok.Builder;

@Builder
public record FilterPedido(
    String codPedido,
    String codCentroDestribuicao,
    String codAbrigo,
    String status
) {
}