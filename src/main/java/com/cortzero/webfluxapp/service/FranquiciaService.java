package com.cortzero.webfluxapp.service;

import com.cortzero.webfluxapp.model.Franquicia;
import com.cortzero.webfluxapp.model.ProductoSucursal;
import com.cortzero.webfluxapp.repository.FranquiciaRepository;
import com.cortzero.webfluxapp.repository.ProductoSucursalRepository;
import com.cortzero.webfluxapp.repository.SucursalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class FranquiciaService {

    private final FranquiciaRepository franquiciaRepository;
    private final SucursalRepository sucursalRepository;
    private final ProductoSucursalRepository productoSucursalRepository;

    public Mono<Franquicia> create(Franquicia franquicia) {
        return franquiciaRepository.findByNombre(franquicia.getNombre())
                .hasElement()
                .flatMap(exists -> exists ?
                        Mono.error(() -> new RuntimeException("La franquicia ya existe"))
                        : franquiciaRepository.save(franquicia));
    }

    public Mono<Franquicia> update(long id, Franquicia franquicia) {
        return franquiciaRepository.findByNombre(franquicia.getNombre())
                .hasElement()
                .flatMap(isNameRepeated -> isNameRepeated ?
                        Mono.error(() -> new RuntimeException(String
                                .format("El nombre '%s' ya está tomado por una franquicia.", franquicia.getNombre())))
                        : franquiciaRepository.findById(id)
                            .flatMap(existingFranquicia -> {
                                existingFranquicia.setNombre(franquicia.getNombre());
                                return franquiciaRepository.save(existingFranquicia);
                            })
                            .switchIfEmpty(Mono.error(() -> new RuntimeException(String
                                    .format("La franquicia con id '%d' no existe.", id))))
                );
    }

    public Flux<ProductoSucursal> getMaxStockProductosPerSucursal(long franquiciaId) {
        return sucursalRepository.findByFranquiciaId(franquiciaId)
                .flatMap(sucursal -> productoSucursalRepository
                        .findBySucursalId(sucursal.getId())
                        .sort(Comparator.comparing(ProductoSucursal::getStock).reversed())
                        .next()
                );
    }

    public Flux<Franquicia> getAllFranquicias() {
        return franquiciaRepository.findAll();
    }

}
