package com.pes.pedido_ms.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.pes.pedido_ms.domain.Pedido;

@Repository
public interface PedidoRepository extends MongoRepository<Pedido, String> {

    Optional<Pedido> findByCodPedido(String codPedido);
}
