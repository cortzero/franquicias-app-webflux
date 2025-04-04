package com.cortzero.webfluxapp.handlers;

import com.cortzero.webfluxapp.model.Franquicia;
import com.cortzero.webfluxapp.service.FranquiciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FranquiciaHandler {

    private final FranquiciaService franquiciaService;

    public Mono<ServerResponse> createFranquicia(ServerRequest request) {
        Mono<Franquicia> franquiciaMono = request.bodyToMono(Franquicia.class);
        return franquiciaMono.flatMap(franquicia -> ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(franquiciaService.create(franquicia), Franquicia.class));
    }

    public Mono<ServerResponse> getMaxStockProductosPerSucursal(ServerRequest request) {
        long franquiciaId = Long.parseLong(request.pathVariable("franquiciaId"));
        return franquiciaService.getMaxStockProductosPerSucursal(franquiciaId)
                .collectList()
                .flatMap(list -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(list)
                );
    }

}
