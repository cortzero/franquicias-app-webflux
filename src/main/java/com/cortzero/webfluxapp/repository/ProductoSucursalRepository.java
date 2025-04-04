package com.cortzero.webfluxapp.repository;

import com.cortzero.webfluxapp.model.ProductoSucursal;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProductoSucursalRepository extends ReactiveCrudRepository<ProductoSucursal, Long> {

    Mono<ProductoSucursal> findByProductoIdAndSucursalId(long productoId, long sucursalId);

    @Query("DELETE FROM producto_sucursal WHERE producto_id = :productoId AND sucursal_id = :sucursalId")
    Mono<Void> deleteProductoFromSucursal(long productoId, long sucursalId);

}
