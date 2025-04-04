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
        long sucursalId = Long.parseLong(request.pathVariable("sucursalId"));
        Mono<ProductoSucursal> productoSucursalMono = request.bodyToMono(ProductoSucursal.class);
        return productoSucursalMono.flatMap(productoSucursal -> {
            productoSucursal.setSucursalId(sucursalId);
            return ServerResponse
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(productoSucursalService.addProductToSucursal(productoSucursal), ProductoSucursal.class);
        });
    }

    public Mono<ServerResponse> removeProductoFromSucursal(ServerRequest request) {
        long productoId = Long.parseLong(request.pathVariable("productoId"));
        long sucursalId = Long.parseLong(request.pathVariable("sucursalId"));
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productoSucursalService.removeProductoFromSucursal(productoId, sucursalId), Void.class);
    }

}
