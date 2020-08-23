package net.cloudburo.hexagon.demo.config;


import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import net.cloudburo.hexagon.demo.protocol.converter.http.json.avro.AvroJsonHttpMessageConverter;


/**
 * Spring configuration class for configuring {@link WebMvcConfigurer}
 */
@Configuration
@EnableWebMvc
public class SpringWebConfig implements WebMvcConfigurer {
    @Override
    public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {
        converters.add(0, new AvroJsonHttpMessageConverter());
    }

    /**
     * We enhance the default RestTemplateBuilder provided by Spring Boot
     * with our message converters
     * https://www.baeldung.com/spring-rest-template-builder
     */
    @Bean
    public RestTemplate restTemplate(final RestTemplateBuilder builder) {
        final RestTemplate restTemplate = builder.build();
        restTemplate.getMessageConverters().add(0, new AvroJsonHttpMessageConverter());
        return restTemplate;
    }

}

