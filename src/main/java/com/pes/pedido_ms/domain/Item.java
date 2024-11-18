package com.pes.pedido_ms.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "items")
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String codItem;
    private String name;
    private Integer quantity;
}