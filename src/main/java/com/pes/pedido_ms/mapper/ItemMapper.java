package com.pes.pedido_ms.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.pes.pedido_ms.controller.request.ItemPedidoRequest;
import com.pes.pedido_ms.domain.Item;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ItemMapper {

    default List<Item> toItemEntity(List<ItemPedidoRequest> itemsRequest) {
        return itemsRequest.stream()
                .map(it -> Item.builder()
                        .codItem(it.getCodItem())
                        .name(it.getName())
                        .quantity(it.getQuantity())
                        .build())
                .toList();
    }
}
