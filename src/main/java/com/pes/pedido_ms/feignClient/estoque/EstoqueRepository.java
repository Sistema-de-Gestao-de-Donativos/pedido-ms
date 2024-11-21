package com.pes.pedido_ms.feignClient.estoque;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pes.pedido_ms.controller.request.ItemPedidoRequest;
import com.pes.pedido_ms.domain.Item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class EstoqueRepository {

    private final EstoqueClient estoqueClient;
    private final EstoqueConverter estoqueConverter;

    public List<Item> buscarItemEstoque(final String codigoItem, final Long codCD){
        log.info("Buscando item no estoque com código: {}", codigoItem);
        List<EstoqueItemDTO> estoqueItemDTO = estoqueClient.buscarItem(codigoItem, codCD);
        return estoqueConverter.estoqueItemDTOToItem(estoqueItemDTO);
    }

    public void atualizarItem(final Item item) {
        log.info("Atualizando item no estoque: {}", item);
        EstoqueItemDTO estoqueItemDTO = estoqueConverter.itemToEstoqueItemDTO(item);
        estoqueClient.atualizarItem(item.getCodItem(), estoqueItemDTO);
    }

    public void deletarItem(final ItemPedidoRequest item, final Long codCd) {
        log.info("Deletando item no estoque: {}", item);
        // codcd, nome, qtd
        estoqueClient.saidaEstoque(codCd,item.getName(),item.getQuantity());
    }
    
}
