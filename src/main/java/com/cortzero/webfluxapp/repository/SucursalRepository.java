package com.cortzero.webfluxapp.repository;

import com.cortzero.webfluxapp.model.Sucursal;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface SucursalRepository extends ReactiveCrudRepository<Sucursal, Long> {

    Mono<Sucursal> findByNombre(String nombre);
    Flux<Sucursal> findByFranquiciaId(long franquiciaId);

}
