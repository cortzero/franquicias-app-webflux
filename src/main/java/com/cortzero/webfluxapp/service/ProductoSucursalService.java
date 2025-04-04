package com.cortzero.webfluxapp.service;

import com.cortzero.webfluxapp.model.ProductoSucursal;
import com.cortzero.webfluxapp.repository.ProductoSucursalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductoSucursalService {

    private final ProductoSucursalRepository productoSucursalRepository;

    public Mono<ProductoSucursal> addProductToSucursal(ProductoSucursal productoSucursal) {
        Mono<Boolean> existingProductoSucursalMono = productoSucursalRepository
                .findByProductoIdAndSucursalId(productoSucursal.getProductoId(), productoSucursal.getSucursalId())
                .hasElement();
        return existingProductoSucursalMono
                .flatMap(exists -> exists ?
                        Mono.error(() -> new RuntimeException("El producto ya está agregado a la sucursal"))
                        : productoSucursalRepository.save(productoSucursal));
    }

}
