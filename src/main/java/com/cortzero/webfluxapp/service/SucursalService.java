package com.cortzero.webfluxapp.service;

import com.cortzero.webfluxapp.model.Sucursal;
import com.cortzero.webfluxapp.repository.FranquiciaRepository;
import com.cortzero.webfluxapp.repository.SucursalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SucursalService {

    private final SucursalRepository sucursalRepository;
    private final FranquiciaRepository franquiciaRepository;

    public Mono<Sucursal> create(Sucursal sucursal) {
        Mono<Boolean> franquiciaExists = franquiciaRepository.findById(sucursal.getFranquiciaId()).hasElement();
        Mono<Boolean> sucursalExists = sucursalRepository.findByNombre(sucursal.getNombre()).hasElement();
        return sucursalExists.flatMap(
                existSucursal -> existSucursal ?
                        Mono.error(() -> new RuntimeException("La sucursal ya existe"))
                        : franquiciaExists.flatMap(
                                existFranquicia -> existFranquicia ?
                                        sucursalRepository.save(sucursal)
                                        : Mono.error(() -> new RuntimeException("No se encontró la franquicia"))
                ));
    }

}
