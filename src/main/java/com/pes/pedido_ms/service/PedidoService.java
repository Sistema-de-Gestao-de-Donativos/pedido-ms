package com.pes.pedido_ms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pes.pedido_ms.controller.request.CreatePedidoRequest;
import com.pes.pedido_ms.controller.request.ItemPedidoRequest;
import com.pes.pedido_ms.controller.request.UpdatePedidoStatus;
import com.pes.pedido_ms.controller.response.PedidoCreationResponse;
import com.pes.pedido_ms.domain.Item;
import com.pes.pedido_ms.domain.Pedido;
import com.pes.pedido_ms.feignClient.estoque.EstoqueRepository;
import com.pes.pedido_ms.mapper.ItemMapper;
import com.pes.pedido_ms.mapper.PedidoMapper;
import com.pes.pedido_ms.repository.ItemRepository;
import com.pes.pedido_ms.repository.PedidoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private PedidoQuery pedidoQuery;

    @Transactional
    public PedidoCreationResponse createPedido(CreatePedidoRequest request) {
        List<ItemPedidoRequest> itemsNotFound = new ArrayList<>();

        request.getItems().forEach(it -> {
            List<Item> item = estoqueRepository.buscarItemEstoque(it.getName(),Long.parseLong(request.getCodCentroDestribuicao()));
            
            if(item.isEmpty() || (item.get(0).getQuantity() < it.getQuantity())) {
                itemsNotFound.add(it);
            }
        });

        if (!itemsNotFound.isEmpty()) {
            return new PedidoCreationResponse(itemsNotFound, false);
        }

        // Dar baixa no estoque
        request.getItems().forEach(it -> {
            estoqueRepository.deletarItem(it, Long.parseLong(request.getCodCentroDestribuicao()));
        });

        List<Item> savedItems = itemRepository.saveAll(itemMapper.toItemEntity(request.getItems()));

        Pedido pedido = pedidoMapper.toPedidoEntity(request, savedItems);
        pedido.preInclusao();
        pedidoRepository.save(pedido);

        return new PedidoCreationResponse(null, true);
    }

    public List<Pedido> buscarPedido(final FilterPedido filter){
        List<Pedido> pedidos = mongoTemplate.find(pedidoQuery.filter(filter), Pedido.class);
        return pedidos;
    }

    public Pedido update(UpdatePedidoStatus updatedStatus) {

        Optional<Pedido> oldPedido = pedidoRepository.findByCodPedido(updatedStatus.getCodPedido());

        if (oldPedido.isEmpty()) {
            throw new RuntimeException("Pedido com codigo " + updatedStatus.getCodPedido() + " e invalido.");
        }
        
        Pedido updatedPedido = oldPedido.get();
        updatedPedido.setStatus(updatedStatus.getStatus());
        
        return pedidoRepository.save(updatedPedido);
    }
}
