package com.pes.pedido_ms.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.pes.pedido_ms.domain.enums.StatusPedidoDomain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;

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

    public void preInclusao() {
        this.status = StatusPedidoDomain.PROCESSANDO;
        this.usuario = null; // caso tenha autenticação JwtUtils.buscarUsuario()
        this.dataAbertura = LocalDateTime.now();
    }

    public void recusa() {
        this.status = StatusPedidoDomain.REPROVADO;
        this.usuario = null;
        this.dataAbertura = LocalDateTime.now();
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

}