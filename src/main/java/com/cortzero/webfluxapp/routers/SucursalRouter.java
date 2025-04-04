package com.cortzero.webfluxapp.routers;

import com.cortzero.webfluxapp.handlers.SucursalHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class SucursalRouter {

    private static final String PATH = "/api/sucursales";

    @Bean
    public RouterFunction<ServerResponse> sucursalRoute(SucursalHandler handler) {
        return RouterFunctions.route()
                .POST(PATH, handler::createSucursal)
                .build();
    }

}
