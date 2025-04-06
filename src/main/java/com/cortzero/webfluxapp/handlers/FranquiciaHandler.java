package com.cortzero.webfluxapp.handlers;

import com.cortzero.webfluxapp.model.Franquicia;
import com.cortzero.webfluxapp.service.FranquiciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class FranquiciaHandler {

    private final FranquiciaService franquiciaService;

    public Mono<ServerResponse> createFranquicia(ServerRequest request) {
        return request.bodyToMono(Franquicia.class)
                .flatMap(franquiciaService::create)
                .flatMap(franquiciaCreated -> ServerResponse
                    .created(URI.create(request.path() + "/" + franquiciaCreated.getId()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(franquiciaCreated))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(e.getMessage()));
    }

    public Mono<ServerResponse> updateFranquicia(ServerRequest request) {
        long id;
        try {
            id = Long.parseLong(request.pathVariable("franquiciaId"));
        } catch (NumberFormatException e) {
            return ServerResponse.badRequest().bodyValue("Formato incorrecto de la URL: " + e.getMessage());
        }
        return request.bodyToMono(Franquicia.class)
                .flatMap(franquicia -> franquiciaService.update(id, franquicia))
                .flatMap(updatedFranquicia -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(updatedFranquicia))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(e.getMessage()));
    }

    public Mono<ServerResponse> getMaxStockProductosPerSucursal(ServerRequest request) {
        long franquiciaId;
        try {
            franquiciaId = Long.parseLong(request.pathVariable("franquiciaId"));
        } catch (NumberFormatException e) {
            return ServerResponse.badRequest().bodyValue("Formato incorrecto de la URL: " + e.getMessage());
        }
        return franquiciaService.getMaxStockProductosPerSucursal(franquiciaId)
                .collectList()
                .flatMap(list -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(list)
                );
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(franquiciaService.getAllFranquicias(), Franquicia.class);
    }

}
