package com.pes.pedido_ms.repository;

import com.pes.pedido_ms.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    Optional<Pedido> findByCode(Long code);
}