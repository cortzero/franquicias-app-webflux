package com.cortzero.webfluxapp.routers;

import com.cortzero.webfluxapp.handlers.ProductoHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ProductoRouter {

    private static final String PATH = "/api/productos";

    @Bean
    public RouterFunction<ServerResponse> routeProducto(ProductoHandler handler) {
        return RouterFunctions.route()
                .GET(PATH, handler::getAll)
                .POST(PATH, handler::createProducto)
                .build();
    }

}
