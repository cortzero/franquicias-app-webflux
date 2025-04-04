package com.cortzero.webfluxapp.service;

import com.cortzero.webfluxapp.model.Franquicia;
import com.cortzero.webfluxapp.repository.FranquiciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FranquiciaService {

    private final FranquiciaRepository franquiciaRepository;

    public Mono<Franquicia> create(Franquicia franquicia) {
        Mono<Boolean> franquiciaExists = franquiciaRepository.findByNombre(franquicia.getNombre()).hasElement();
        return franquiciaExists.flatMap(
                exists -> exists ?
                        Mono.error(() -> new RuntimeException("La franquicia ya existe"))
                        : franquiciaRepository.save(franquicia));
    }

}
