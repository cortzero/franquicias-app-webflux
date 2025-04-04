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
        return franquiciaRepository.save(franquicia);
    }

}
