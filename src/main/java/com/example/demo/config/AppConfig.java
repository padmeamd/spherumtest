package com.example.demo.config;

import com.google.gson.Gson;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Gson gson() {
        return new Gson();
    }

    @Bean
    public String path(ApplicationArguments args) {
        final String[] arguments = args.getSourceArgs();
        if (!arguments[0].endsWith(".json")) {
            throw new IllegalStateException("Illegal input data format. Input data format must be [.json]");
        }
        return arguments[0];
    }
}
