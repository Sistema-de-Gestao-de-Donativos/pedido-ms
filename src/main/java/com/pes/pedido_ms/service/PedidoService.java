package com.pes.pedido_ms.service;

import static com.pes.pedido_ms.mapper.PedidoMapper.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.mongodb.core.MongoTemplate;
import com.pes.pedido_ms.controller.request.CreatePedidoRequest;
import com.pes.pedido_ms.controller.request.UpdatePedidoStatus;
import com.pes.pedido_ms.controller.response.PedidoCreationResponse;
import com.pes.pedido_ms.domain.Item;
import com.pes.pedido_ms.domain.Pedido;
import com.pes.pedido_ms.feignClient.estoque.EstoqueRepository;
import com.pes.pedido_ms.mapper.PedidoMapper;
import com.pes.pedido_ms.repository.PedidoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private PedidoQuery pedidoQuery;

    @Transactional
    public PedidoCreationResponse createPedido(CreatePedidoRequest request) {

        List<Item> itemsNotFound = new ArrayList<>();

        //descomentar só quando o estoque estiver ok
        //verificar quais campos o estoque retorna, precijamos só do cod/id do item
        // request.getItems().forEach(it -> {
        //     List<Item> item = estoqueRepository.buscarItemEstoque(it.getCodItem());
        //     if(item.isEmpty()){
        //         itemsNotFound.add(it);
        //     }
        // });

        if (!itemsNotFound.isEmpty()) {
            return new PedidoCreationResponse(itemsNotFound, false);
        }

        request.getItems().forEach(it -> {
            Pedido pedido = pedidoMapper.toPedidoEntity(request);
            pedido.preInclusao();
            pedidoRepository.save(pedido);
        });

        return new PedidoCreationResponse(null, true);
    }

    public List<Pedido> buscarPedido(final FilterPedido filter){
        List<Pedido> pedidos = mongoTemplate.find(pedidoQuery.filter(filter), Pedido.class);
        return pedidos;
    }

    public Boolean update(UpdatePedidoStatus updatedStatus) {

        Optional<Pedido> oldPedido = pedidoRepository.findByCodPedido(updatedStatus.getCodPedido().toString());

        if (oldPedido.isEmpty()) {
            return false;
        }
        
        // atualiza
        Pedido updatedPedido = oldPedido.get();
        updatedPedido.setStatus(updatedStatus.getStatus());
        // salva
        pedidoRepository.save(updatedPedido);
        
        return true;
    }
}
