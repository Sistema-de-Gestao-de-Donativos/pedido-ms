package com.pes.pedido_ms.feignClient.estoque;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.pes.pedido_ms.domain.Item;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EstoqueConverter {

    List<Item> estoqueItemDTOToItem(List<EstoqueItemDTO> estoqueItemDTO);

    EstoqueItemDTO itemToEstoqueItemDTO(Item item);

    default Item map(EstoqueItemDTO estoqueItemDTO) {
        if (estoqueItemDTO == null) {
            return null;
        }
        return new Item(
            null,
            estoqueItemDTO.codItem(),
            estoqueItemDTO.quantity()
        );
    }
}
