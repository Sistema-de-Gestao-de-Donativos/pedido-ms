package com.pes.pedido_ms.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "items")
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String codItem;
    private Integer quantity;
}
