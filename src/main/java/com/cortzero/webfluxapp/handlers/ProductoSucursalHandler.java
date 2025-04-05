package com.cortzero.webfluxapp.handlers;

import com.cortzero.webfluxapp.model.ProductoSucursal;
import com.cortzero.webfluxapp.service.ProductoSucursalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductoSucursalHandler {

    private final ProductoSucursalService productoSucursalService;

    public Mono<ServerResponse> addProductoToSucursal(ServerRequest request) {
        long sucursalId;
        try {
            sucursalId = Long.parseLong(request.pathVariable("sucursalId"));
        } catch (NumberFormatException e) {
            return ServerResponse.badRequest().bodyValue("Formato incorrecto de la URL: " + e.getMessage());
        }
        return request.bodyToMono(ProductoSucursal.class)
                .flatMap(productoSucursal -> {
                    productoSucursal.setSucursalId(sucursalId);
                    return productoSucursalService.addProductToSucursal(productoSucursal);
                })
                .flatMap(productoSucursalCreated -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(productoSucursalCreated))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(e.getMessage()));
    }

    public Mono<ServerResponse> removeProductoFromSucursal(ServerRequest request) {
        long productoId;
        long sucursalId;
        try {
            productoId = Long.parseLong(request.pathVariable("productoId"));
            sucursalId = Long.parseLong(request.pathVariable("sucursalId"));
        } catch (NumberFormatException e) {
            return ServerResponse.badRequest().bodyValue("Formato incorrecto de la URL: " + e.getMessage());
        }
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productoSucursalService.removeProductoFromSucursal(productoId, sucursalId), Void.class);
    }

    public Mono<ServerResponse> changeProductoStockInSucursal(ServerRequest request) {
        long productoId;
        long sucursalId;
        try {
            productoId = Long.parseLong(request.pathVariable("productoId"));
            sucursalId = Long.parseLong(request.pathVariable("sucursalId"));
        } catch (NumberFormatException e) {
            return ServerResponse.badRequest().bodyValue("Formato incorrecto de la URL: " + e.getMessage());
        }
        Mono<ProductoSucursal> productoSucursalMono = request.bodyToMono(ProductoSucursal.class);
        return productoSucursalMono.flatMap(productoSucursal -> {
            int newStockAmount = productoSucursal.getStock();
            return ServerResponse
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(productoSucursalService.changeProductoStock(productoId, sucursalId, newStockAmount), Void.class);
        });
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
        long sucursalId;
        try {
            sucursalId = Long.parseLong(request.pathVariable("sucursalId"));
        } catch (NumberFormatException e) {
            return ServerResponse.badRequest().bodyValue("Formato incorrecto de la URL: " + e.getMessage());
        }
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productoSucursalService.getAllProductsInSucursal(sucursalId), ProductoSucursal.class);
    }

}
