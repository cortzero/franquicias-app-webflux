package com.cortzero.webfluxapp.service;

import com.cortzero.webfluxapp.model.Producto;
import com.cortzero.webfluxapp.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;

    public Mono<Producto> create(Producto producto) {
        return productoRepository.findByNombre(producto.getNombre())
                .hasElement()
                .flatMap(existsProducto -> existsProducto ?
                        Mono.error(() -> new RuntimeException("El producto ya está registrado"))
                        : productoRepository.save(producto));
    }

    public Mono<Producto> update(long id, Producto producto) {
        return productoRepository.findByNombre(producto.getNombre())
                .hasElement()
                .flatMap(isNameRepeated -> isNameRepeated ?
                        Mono.error(() -> new RuntimeException(
                                String.format("Ya existe un producto '%s'.", producto.getNombre())))
                        : productoRepository.findById(id)
                            .flatMap(existingProducto -> {
                                existingProducto.setNombre(producto.getNombre());
                                return productoRepository.save(existingProducto);
                            })
                            .switchIfEmpty(Mono.error(() -> new RuntimeException(
                                    String.format("El producto con id '%d' no existe.", id))))
                );
    }

    public Flux<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

}
