package com.pes.pedido_ms.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.pes.pedido_ms.domain.Item;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {
}

