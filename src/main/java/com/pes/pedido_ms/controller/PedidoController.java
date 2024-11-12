package com.pes.pedido_ms.controller;

import com.pes.pedido_ms.controller.request.CreatePedidoRequest;
import com.pes.pedido_ms.controller.response.PedidoCreationResponse;
import com.pes.pedido_ms.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/v1/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Operation(summary = "Create Pedido or return error.", description = "Create Pedido if all items are available, or return error with no available items.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoCreationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid Request Body", content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"error\": [\n" + "\"Item is required\"\n" + "]}"))),
    })
    @PostMapping
    public ResponseEntity<PedidoCreationResponse> create(@Valid @RequestBody CreatePedidoRequest request) {
        PedidoCreationResponse response = pedidoService.createPedido(request);

        if (response.isSuccess()) {
            return ResponseEntity.status(CREATED).body(response);
        } else {
            return ResponseEntity.status(BAD_REQUEST).body(response);
        }
    }
}