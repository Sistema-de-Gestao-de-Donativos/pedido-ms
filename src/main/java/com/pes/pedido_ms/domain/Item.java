package com.pes.pedido_ms.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    @JsonProperty("_id")
    private String codItem;
    @JsonProperty("quantidade")
    private Integer quantity;
}
/* 
 * {
        "codCd": 1,
        "nome": "feijao",
        "quantidade": 57,
        "unidade": "Kg",
        "categoria": "alimenticio",
        "_id": "671ab2e5d5a8454b304700f9",
        "created_at": "2024-10-24T20:49:41.755000"
    }
 */