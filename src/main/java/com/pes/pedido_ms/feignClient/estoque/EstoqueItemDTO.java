package com.pes.pedido_ms.feignClient.estoque;

public record EstoqueItemDTO(
    String _id,
    String nome,
    Integer quantidade
) {
}
