package com.pes.pedido_ms.mapper;

import com.pes.pedido_ms.controller.request.CreatePedidoRequest;
import com.pes.pedido_ms.domain.Pedido;
import com.pes.pedido_ms.domain.enums.StatusPedidoDomain;

import java.time.LocalDateTime;

public class PedidoMapper {

    public static Pedido toEntity(CreatePedidoRequest request) {
        Pedido pedido = new Pedido();

        pedido.setCodCentroDestribuicao(request.getCodCentroDestribuicao());
        pedido.setCodAbrigo(request.getCodAbrigo());
        pedido.setUsuario(request.getUsuario());
        pedido.setStatus(StatusPedidoDomain.APROVADO);
        pedido.setDataAbertura(LocalDateTime.now());
        pedido.setItems(request.getItems());

        return pedido;
    }
}
