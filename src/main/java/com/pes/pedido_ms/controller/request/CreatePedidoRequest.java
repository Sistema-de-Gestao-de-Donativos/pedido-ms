package com.pes.pedido_ms.controller.request;

import com.pes.pedido_ms.domain.Item;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreatePedidoRequest {

    @NotNull(message = "Item is required")
    private List<Item> items = new ArrayList<>();

    @NotBlank(message = "codCentroDestribuicao is required")
    private String codCentroDestribuicao;

    @NotBlank(message = "codAbrigo is required")
    private String codAbrigo;

    @NotBlank(message = "usuario is required")
    private String usuario;
}
