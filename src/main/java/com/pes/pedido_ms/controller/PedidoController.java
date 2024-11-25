package com.pes.pedido_ms.controller;

import static org.springframework.http.HttpStatus.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pes.pedido_ms.controller.request.CreatePedidoRequest;
import com.pes.pedido_ms.controller.request.UpdatePedidoStatus;
import com.pes.pedido_ms.controller.response.PedidoCreationResponse;
import com.pes.pedido_ms.domain.Pedido;
import com.pes.pedido_ms.service.FilterPedido;
import com.pes.pedido_ms.service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/pedidos")
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "bearerAuth")
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

    @Operation(summary = "Buscar pedidos", description = "Buscando pedidos por filtro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoCreationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid Request Body", content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"error\": [\n" + "\"Item is required\"\n" + "]}"))),
    })
    @GetMapping
    public ResponseEntity<List<Pedido>> buscarPedidos(
        @RequestParam(value = "codPedido", required = false) String codPedido,
        @RequestParam(value = "codCentroDestribuicao", required = false) String codCentroDestribuicao,
        @RequestParam(value = "codAbrigo", required = false) String codAbrigo,
        @RequestParam(value = "status", required = false) String status) {

        FilterPedido filter = FilterPedido.builder()
        .codPedido(codPedido)
        .codCentroDestribuicao(codCentroDestribuicao)
        .codPedido(codPedido)
        .status(status)
        .build();
        
        return ResponseEntity.status(HttpStatus.OK).body(pedidoService.buscarPedido(filter));
    }

    @Operation(summary = "Atualizar pedido", description = "Seta o novo status de um pedido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido's status updated"),
        @ApiResponse(responseCode = "404", description = "Pedido not found")
    })
    @PutMapping
    public ResponseEntity<Void> update(@Valid @RequestBody UpdatePedidoStatus updatedStatus) {
        try {
            pedidoService.update(updatedStatus);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
