package com.pes.pedido_ms.controller.response;

import com.pes.pedido_ms.domain.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoCreationResponse {

    private List<Item> itemsNotFound;
    private boolean success;
}
