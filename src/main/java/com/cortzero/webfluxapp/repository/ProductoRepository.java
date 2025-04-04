package com.cortzero.webfluxapp.repository;

import com.cortzero.webfluxapp.model.Producto;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProductoRepository extends ReactiveCrudRepository<Producto, Long> {

    Mono<Producto> findByNombre(String nombre);

}
