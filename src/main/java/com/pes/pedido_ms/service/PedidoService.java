package com.pes.pedido_ms.service;

import static com.pes.pedido_ms.mapper.PedidoMapper.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pes.pedido_ms.controller.request.CreatePedidoRequest;
import com.pes.pedido_ms.controller.response.PedidoCreationResponse;
import com.pes.pedido_ms.domain.Item;
import com.pes.pedido_ms.domain.Pedido;
import com.pes.pedido_ms.repository.PedidoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional
    public PedidoCreationResponse createPedido(CreatePedidoRequest request) {

        List<Item> itemsFound = new ArrayList<>();
        List<Item> itemsNotFound = new ArrayList<>();

        for (Item item : request.getItems()) {
            // verifica no estoque se item esta disponivel
            // presumo que como retorna Long, 0 eh indisponivel, outro numero eh o codItem
            // Long codItem = verificaItemEstoque(item.getName(), request.getCodCd());
            Long codItem = 0L;

            if (codItem == 0) {
                itemsNotFound.add(item);
            } else {
                itemsFound.add(item);
            }
        }

        if (itemsNotFound.isEmpty()) {
            // deve retornar ok e criar o pedido
            for (Item item : itemsFound) {
                Pedido pedido = toEntity(request);
                pedidoRepository.save(pedido);
            }
            return new PedidoCreationResponse(null, true);

        } else {
            // deve retornar erro e os itens não encontrados
            return new PedidoCreationResponse(itemsNotFound, false);
        }
    }
}
