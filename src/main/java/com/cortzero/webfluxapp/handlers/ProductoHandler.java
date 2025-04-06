package com.cortzero.webfluxapp.handlers;

import com.cortzero.webfluxapp.model.Producto;
import com.cortzero.webfluxapp.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class ProductoHandler {

    private final ProductoService productoService;

    public Mono<ServerResponse> createProducto(ServerRequest request) {
        return request.bodyToMono(Producto.class)
                .flatMap(productoService::create)
                .flatMap(createdProducto -> ServerResponse
                    .created(URI.create(request.path() + "/" + createdProducto.getId()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(createdProducto))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(e.getMessage()));
    }

    public Mono<ServerResponse> updateProducto(ServerRequest request) {
        long id;
        try {
            id = Long.parseLong(request.pathVariable("productoId"));
        } catch (NumberFormatException e) {
            return ServerResponse.badRequest().bodyValue("Formato incorrecto de la URL: " + e.getMessage());
        }
        return request.bodyToMono(Producto.class)
                .flatMap(producto -> productoService.update(id, producto))
                .flatMap(updatedProducto -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(updatedProducto))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(e.getMessage()));
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productoService.getAllProductos(), Producto.class);
    }

}
