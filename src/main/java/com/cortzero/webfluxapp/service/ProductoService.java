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
        Mono<Boolean> productoExists = productoRepository.findByNombre(producto.getNombre()).hasElement();
        return productoExists.flatMap(
                existsProducto -> existsProducto ?
                        Mono.error(() -> new RuntimeException("El producto ya está registrado"))
                        : productoRepository.save(producto));
    }

    public Flux<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

}
