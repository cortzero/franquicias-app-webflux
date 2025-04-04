package com.cortzero.webfluxapp.handlers;

import com.cortzero.webfluxapp.model.Producto;
import com.cortzero.webfluxapp.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductoHandler {

    private final ProductoService productoService;

    public Mono<ServerResponse> createProducto(ServerRequest request) {
        Mono<Producto> productoMono = request.bodyToMono(Producto.class);
        return productoMono.flatMap(producto -> ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productoService.create(producto), Producto.class));
    }

}
