package com.example.demo.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.Properties;

public class CustomEnvPostProcessor implements EnvironmentPostProcessor {

    private static final String DEFAULT_LOG_FILE_PROP = "logging.file.name";

    private static final String ARG_1 = "arg1";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        if (System.getProperty(ARG_1) == null) {
            System.out.println("Logging file didn't specified. Using default logging file :" + environment.getProperty(DEFAULT_LOG_FILE_PROP));
            return;
        }
        Properties props = new Properties();
        if (environment.containsProperty(DEFAULT_LOG_FILE_PROP)) {
            environment.getPropertySources().remove(DEFAULT_LOG_FILE_PROP);
        }
        props.put(DEFAULT_LOG_FILE_PROP, System.getProperty(ARG_1));
        environment.getPropertySources().addFirst(new PropertiesPropertySource("logfile", props));
        System.out.println(String.format("Using specified logging file: %s", System.getProperty(ARG_1)));
    }
}

