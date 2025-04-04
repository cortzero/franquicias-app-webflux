package com.cortzero.webfluxapp.repository;

import com.cortzero.webfluxapp.model.Franquicia;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface FranquiciaRepository extends ReactiveCrudRepository<Franquicia, Long> {

    Mono<Franquicia> findByNombre(String nombre);

}
