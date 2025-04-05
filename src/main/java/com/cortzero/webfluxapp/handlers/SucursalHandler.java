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
        return request.bodyToMono(Sucursal.class)
                .flatMap(sucursalService::create)
                .flatMap(createdSucursal -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(createdSucursal))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(e.getMessage()));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        long id;
        try {
            id = Long.parseLong(request.pathVariable("sucursalId"));
        } catch (NumberFormatException e) {
            return ServerResponse.badRequest().bodyValue("Formato incorrecto de la URL: " + e.getMessage());
        }
        return request.bodyToMono(Sucursal.class)
                .flatMap(sucursal -> sucursalService.update(id, sucursal))
                .flatMap(updatedSucursal -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(updatedSucursal))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(e.getMessage()));
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(sucursalService.getAllSucursales(), Sucursal.class);
    }

}
