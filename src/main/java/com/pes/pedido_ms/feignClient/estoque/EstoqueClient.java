package com.pes.pedido_ms.feignClient.estoque;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "app", path = "/v1/stock", url = "${estoque-ms.url:#{null}}")
public interface EstoqueClient {

    @GetMapping(value = "/{codCd}/{nameItem}")
    List<EstoqueItemDTO> buscarItem(@PathVariable("nameItem") final String nameItem,
                                    @PathVariable("codCd") final Long codigoCd);

    @PutMapping(value = "/item/{codigoItem}/update")
    void atualizarItem(@PathVariable("codigoItem") final String codigo,
                        @RequestBody final EstoqueItemDTO item);
    
    @DeleteMapping(value = "/{codCd}")
    void saidaEstoque(@PathVariable("codCd") final String codCd,
                      @RequestParam("codBarras") final String codBarras, 
                      @RequestParam("qtd") Long quantidade);
}