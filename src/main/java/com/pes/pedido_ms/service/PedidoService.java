package com.pes.pedido_ms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.mongodb.core.MongoTemplate;
import com.pes.pedido_ms.controller.request.CreatePedidoRequest;
import com.pes.pedido_ms.controller.request.ItemPedidoRequest;
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
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        List<ItemPedidoRequest> itemsNotFound = new ArrayList<>();

        //descomentar só quando o estoque estiver ok
        //verificar quais campos o estoque retorna, precisamos só do cod/id do item
        // request.getItems().forEach(it -> {
        //     List<Item> item = estoqueRepository.buscarItemEstoque(it.getName(),Long.parseLong(request.getCodCentroDestribuicao()));
        //     if(item.isEmpty()){
        //         itemsNotFound.add(it);
        //     }
        //     System.out.println("blablabal\nSize:");
        //     System.out.println(item.size());
        //     System.out.println(item.get(0));
        //     if (item.get(0).getQuantity() < it.getQuantity()) {
        //         itemsNotFound.add(it);
        //     }
        // });
        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
        // Lista de itens retornada pelo estoque
        /* 
         * [
                {
                    "codCd": 1,
                    "nome": "feijao",
                    "quantidade": 57,
                    "unidade": "Kg",
                    "categoria": "alimenticio",
                    "_id": "671ab2e5d5a8454b304700f9",
                    "created_at": "2024-10-24T20:49:41.755000"
                }
            ]
         */

        if (!itemsNotFound.isEmpty()) {
            return new PedidoCreationResponse(itemsNotFound, false);
        }
        System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCccc");

        request.getItems().forEach(it -> {
            Pedido pedido = pedidoMapper.toPedidoEntity(request); // possiveis problemas aqui
            pedido.preInclusao();
            pedidoRepository.save(pedido);
            System.out.println("salvou com sucesso");
            System.out.println("pedido: "+pedido);
            System.out.println("ID: "+pedido.getCodPedido());
        });
        System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");

        return new PedidoCreationResponse(null, true);
    }

    // Funcionando
    public List<Pedido> buscarPedido(final FilterPedido filter){
        List<Pedido> pedidos = mongoTemplate.find(pedidoQuery.filter(filter), Pedido.class);
        return pedidos;
    }

    // Funcionando
    public Boolean update(UpdatePedidoStatus updatedStatus) {

        Optional<Pedido> oldPedido = pedidoRepository.findByCodPedido(updatedStatus.getCodPedido().toString());

        if (oldPedido.isEmpty()) {
            return false;
        }
        
        // atualiza
        Pedido updatedPedido = oldPedido.get();
        System.out.println("Status: "+updatedPedido.getStatus());
        updatedPedido.setStatus(updatedStatus.getStatus());
        // salva
        pedidoRepository.save(updatedPedido);

        System.out.println("Status: "+updatedPedido.getStatus());
        
        return true;
    }
}
