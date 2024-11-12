package com.pes.pedido_ms.feignClient.estoque;

public record EstoqueItemDTO(
    String codItem,
    Integer quantity
) {
}
