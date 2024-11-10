package com.pes.pedido_ms.feignClient.estoque;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class EstoqueRepository {

    private final EstoqueClient estoqueClient;
    // private final EstoqueConverter estoqueConverter;

    // public void cadastrarItem(final EstoqueItem item){
    //     log.info("Cadastrando item no estoque: {}", item);
    //     EstoqueItemDTO estoqueItemDTO = estoqueConverter.estoqueItemToEstoqueItemDTO(item);
    //     estoqueClient.cadastrarItem(estoqueItemDTO);
    // }

    // public EstoqueItem buscarItemEstoque(final String codigoItem){
    //     log.info("Buscando item no estoque com código: {}", codigoItem);
    //     EstoqueItemDTO estoqueItemDTO = estoqueClient.buscarItem(codigoItem);
    //     return estoqueConverter.estoqueItemDTOToEstoqueItem(estoqueItemDTO);
    // }

    // public Long verificaItemEstoque(Long codCd, String nameItem) {

    //     try {
    //         log.info("Verificando item no estoque com nome {} no CD {}", nameItem, codCd);
    //         return estoqueClient.verificaItemEstoque(nameItem, codCd);
    //     } catch (Exception e) {
    //         log.info("Item não encontrado: {}", e.getMessage());
    //         return -1L;
    //     }
    // }

    // public void atualizarItem(final EstoqueItem item) {
    //     log.info("Atualizando item no estoque: {}", item);
    //     EstoqueItemDTO estoqueItemDTO = estoqueConverter.estoqueItemToEstoqueItemDTO(item);
    //     estoqueClient.atualizarItem(estoqueItemDTO);
    // }
    
}
