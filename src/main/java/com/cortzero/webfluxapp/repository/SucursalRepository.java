package com.cortzero.webfluxapp.repository;

import com.cortzero.webfluxapp.model.Sucursal;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SucursalRepository extends ReactiveCrudRepository<Sucursal, Long> {
}
