package com.cortzero.webfluxapp.handlers;

import com.cortzero.webfluxapp.model.Sucursal;
import com.cortzero.webfluxapp.service.SucursalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SucursalHandler {

    private final SucursalService sucursalService;

    public Mono<ServerResponse> createSucursal(ServerRequest request) {
        Mono<Sucursal> sucursalMono = request.bodyToMono(Sucursal.class);
        return sucursalMono.flatMap(sucursal -> ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(sucursalService.create(sucursal), Sucursal.class));
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(sucursalService.getAllSucursales(), Sucursal.class);
    }

}
