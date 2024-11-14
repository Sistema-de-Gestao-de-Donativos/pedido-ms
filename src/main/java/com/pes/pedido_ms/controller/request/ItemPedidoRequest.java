package com.pes.pedido_ms.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ItemPedidoRequest {
    
    @JsonProperty("nome")
    private String name;
    @JsonProperty("quantidade")
    private Integer quantity;
}
