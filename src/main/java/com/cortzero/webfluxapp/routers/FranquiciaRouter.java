package com.cortzero.webfluxapp.routers;

import com.cortzero.webfluxapp.handlers.FranquiciaHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class FranquiciaRouter {

    private static final String PATH = "/api/franquicias";

    @Bean
    public RouterFunction<ServerResponse> route(FranquiciaHandler handler) {
        return RouterFunctions.route()
                .POST(PATH, handler::createFranquicia)
                .build();
    }

}
