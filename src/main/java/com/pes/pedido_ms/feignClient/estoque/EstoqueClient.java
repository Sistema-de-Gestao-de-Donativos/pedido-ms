package com.pes.pedido_ms.feignClient.estoque;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(value = "estoque-ms", path = "/v1/stock", url = "${estoque-ms.url:#{null}}")
public interface EstoqueClient {

    @GetMapping(value = "/item/{codigoItem}")
    List<EstoqueItemDTO> buscarItem(@PathVariable("codigoItem") final String codigo);

    @PutMapping(value = "/item/{codigoItem}/update")
    void atualizarItem(@PathVariable("codigoItem") final String codigo,
                        @RequestBody final EstoqueItemDTO item);
}