package com.cortzero.webfluxapp.service;

import com.cortzero.webfluxapp.model.ProductoSucursal;
import com.cortzero.webfluxapp.repository.ProductoRepository;
import com.cortzero.webfluxapp.repository.ProductoSucursalRepository;
import com.cortzero.webfluxapp.repository.SucursalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductoSucursalService {

    private final ProductoSucursalRepository productoSucursalRepository;
    private final ProductoRepository productoRepository;
    private final SucursalRepository sucursalRepository;

    public Mono<ProductoSucursal> addProductToSucursal(ProductoSucursal productoSucursal) {
        Mono<Boolean> existingProductoSucursalMono = productoSucursalRepository
                .findByProductoIdAndSucursalId(productoSucursal.getProductoId(), productoSucursal.getSucursalId())
                .hasElement();
        return existingProductoSucursalMono
                .flatMap(exists -> exists ?
                        Mono.error(() -> new RuntimeException("El producto ya está agregado a la sucursal"))
                        : productoSucursalRepository.save(productoSucursal));
    }

    public Mono<Void> removeProductoFromSucursal(long productoId, long sucursalId) {
        Mono<Boolean> existingProductoMono = productoRepository.findById(productoId).hasElement();
        Mono<Boolean> existingSucursalMono = sucursalRepository.findById(sucursalId).hasElement();
        Mono<Boolean> existingProductoSucursalMono = productoSucursalRepository
                .findByProductoIdAndSucursalId(productoId, sucursalId)
                .hasElement();
        return existingProductoMono.flatMap(
                productoNotExists -> !productoNotExists ?
                        Mono.error(() -> new RuntimeException("El producto no existe"))
                        : existingSucursalMono.flatMap(
                        sucursalNotExists -> !sucursalNotExists ?
                                Mono.error(() -> new RuntimeException("La sucursal no existe"))
                                : existingProductoSucursalMono.flatMap(
                                productoNotInSucursal -> !productoNotInSucursal ?
                                        Mono.error(() -> new RuntimeException("La sucursal no posee este producto"))
                                        : productoSucursalRepository.deleteProductoFromSucursal(productoId, sucursalId)
                                )
                )
        );
    }

    public Mono<Void> changeProductoStock(long productoId, long sucursalId, int newStockAmount) {
        Mono<Boolean> existingProductoMono = productoRepository.findById(productoId).hasElement();
        Mono<Boolean> existingSucursalMono = sucursalRepository.findById(sucursalId).hasElement();
        Mono<Boolean> existingProductoSucursalMono = productoSucursalRepository
                .findByProductoIdAndSucursalId(productoId, sucursalId)
                .hasElement();
        return existingProductoMono.flatMap(
                productoNotExists -> !productoNotExists ?
                        Mono.error(() -> new RuntimeException("El producto no existe"))
                        : existingSucursalMono.flatMap(
                        sucursalNotExists -> !sucursalNotExists ?
                                Mono.error(() -> new RuntimeException("La sucursal no existe"))
                                : existingProductoSucursalMono.flatMap(
                                productoNotInSucursal -> !productoNotInSucursal ?
                                        Mono.error(() -> new RuntimeException("La sucursal no posee este producto"))
                                        : productoSucursalRepository.changeStock(productoId, sucursalId, newStockAmount)
                        )
                )
        );
    }

    public Flux<ProductoSucursal> getAllProductsInSucursal(long sucursalId) {
        return productoSucursalRepository.findBySucursalId(sucursalId);
    }

}
