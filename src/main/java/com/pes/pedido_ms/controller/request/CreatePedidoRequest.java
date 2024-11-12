package com.pes.pedido_ms.controller.request;

import java.util.List;

import com.pes.pedido_ms.domain.Item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;


@Getter
public class CreatePedidoRequest {

    @NotBlank(message = "codCentroDestribuicao is required")
    private String codCentroDestribuicao;

    @NotBlank(message = "codAbrigo is required")
    private String codAbrigo;

    @NotNull(message = "Item is required")
    private List<Item> items;
}
