package com.pes.pedido_ms.feignClient.estoque;

import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(value = "estoque-ms", path = "/v1/stock", url = "${estoque-ms.url:#{null}}")
public interface EstoqueClient {

    // @PostMapping(value = "/item/")
    // void cadastrarItem(@RequestBody final EstoqueItemDTO item);

    // @GetMapping(value = "/item/{codigoItem}")
    // EstoqueItemDTO buscarItem(@PathVariable("codigoItem") final String codigo);

    // @GetMapping(value = "/item/{nameItem}/cd/{codCd}")
    // Long verificaItemEstoque(@PathVariable("nameItem") final String nameItem,
    //                         @PathVariable("codCd") final Long codigoCD);

    // @PutMapping(value = "/item/update")
    // void atualizarItem(@RequestBody final EstoqueItemDTO item);
}