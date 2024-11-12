package com.pes.pedido_ms.controller.request;

import com.pes.pedido_ms.domain.enums.StatusPedidoDomain;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdatePedidoStatus {
    
    @NotNull(message = "Pedido's code is required")
    private Long codPedido;

    @NotNull(message = "Pedido's new status is required")
    private StatusPedidoDomain status;
    
}
