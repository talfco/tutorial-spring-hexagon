package net.cloudburo.hexagon.demo.port.in.files.adapter.camel.cfg;

import net.cloudburo.hexagon.demo.port.in.files.adapter.camel.ContentBasedFileRouter;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;


public class ContentBasedFilerRouter {

    @Configuration
    public class ContentBasedFileRouterConfig extends CamelConfiguration {

        @Bean
        ContentBasedFileRouter getContentBasedFileRouter() {
            return new ContentBasedFileRouter();
        }

        @Override
        public List<RouteBuilder> routes() {
            return Arrays.asList(getContentBasedFileRouter());
        }

    }
}
