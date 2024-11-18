package com.pes.pedido_ms.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.pes.pedido_ms.controller.request.CreatePedidoRequest;
import com.pes.pedido_ms.domain.Item;
import com.pes.pedido_ms.domain.Pedido;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PedidoMapper {

    default Pedido toPedidoEntity(CreatePedidoRequest request, List<Item> items) {
        Pedido pedido = Pedido.builder()
                .codAbrigo(request.getCodAbrigo())
                .codCentroDestribuicao(request.getCodCentroDestribuicao())
                .items(items)
                .build();

        return pedido;
    }
}
