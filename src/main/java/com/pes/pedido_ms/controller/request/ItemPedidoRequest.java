package com.pes.pedido_ms.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemPedidoRequest {
    
    @JsonProperty("nome")
    @NotBlank
    private String name;

    
    @JsonProperty("quantidade")
    @NotNull(message = "A quantidade não pode ser nula")
    @Min(value = 1, message = "A quantidade deve ser maior que zero")
    private Integer quantity;
}
