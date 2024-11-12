package com.pes.pedido_ms.domain;

import com.pes.pedido_ms.domain.enums.StatusPedidoDomain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "pedidos")
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String codPedido;

    private String codCentroDestribuicao;
    private String codAbrigo;
    private String usuario;
    private StatusPedidoDomain status;
    private LocalDateTime dataAbertura;

    @DocumentReference
    private List<Item> items = new ArrayList<>();
}