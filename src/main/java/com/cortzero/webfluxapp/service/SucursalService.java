package com.cortzero.webfluxapp.service;

import com.cortzero.webfluxapp.model.Sucursal;
import com.cortzero.webfluxapp.repository.FranquiciaRepository;
import com.cortzero.webfluxapp.repository.SucursalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SucursalService {

    private final SucursalRepository sucursalRepository;
    private final FranquiciaRepository franquiciaRepository;

    public Mono<Sucursal> create(Sucursal sucursal) {
        Mono<Boolean> franquiciaExists = franquiciaRepository.findById(sucursal.getFranquiciaId()).hasElement();
        return sucursalRepository.findByNombre(sucursal.getNombre())
                .hasElement()
                .flatMap(existSucursal -> existSucursal ?
                        Mono.error(() -> new RuntimeException("La sucursal ya existe."))
                        : franquiciaExists.flatMap(
                        existFranquicia -> existFranquicia ?
                                sucursalRepository.save(sucursal)
                                : Mono.error(() -> new RuntimeException("No se encontró la franquicia."))
                ));
    }

    public Mono<Sucursal> update(long id, Sucursal sucursal) {
        return sucursalRepository.findByNombre(sucursal.getNombre())
                .hasElement()
                .flatMap(isNameRepeated -> isNameRepeated ?
                        Mono.error(() -> new RuntimeException(
                                String.format("Ya existe una sucursal con nombre '%s'.", sucursal.getNombre())))
                        : sucursalRepository.findById(id)
                            .flatMap(existingSucursal -> {
                                existingSucursal.setNombre(sucursal.getNombre());
                                return sucursalRepository.save(existingSucursal);
                            })
                            .switchIfEmpty(Mono.error(() -> new RuntimeException(
                                    String.format("La sucursal con id '%d' no existe.", id))))
                );
    }

    public Flux<Sucursal> getAllSucursales() {
        return sucursalRepository.findAll();
    }

}
