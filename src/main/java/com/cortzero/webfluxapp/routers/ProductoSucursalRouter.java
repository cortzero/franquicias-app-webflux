package com.cortzero.webfluxapp.routers;

import com.cortzero.webfluxapp.handlers.ProductoSucursalHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ProductoSucursalRouter {

    private static final String PATH = "/api/sucursales/{sucursalId}/productos";

    @Bean
    public RouterFunction<ServerResponse> routeProductoSucursal(ProductoSucursalHandler handler) {
        return RouterFunctions.route()
                .POST(PATH, handler::addProductoToSucursal)
                .DELETE(PATH + "/{productoId}", handler::removeProductoFromSucursal)
                .build();
    }

}
