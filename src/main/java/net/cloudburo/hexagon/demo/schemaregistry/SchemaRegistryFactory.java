package net.cloudburo.hexagon.demo.schemaregistry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchemaRegistryFactory {

    private static final Logger logger = LoggerFactory.getLogger(SchemaRegistryFactory.class);
    public static SchemaRegistry registry = null;

    public static SchemaRegistry getSchemaRegistry(String className) throws Exception {
        if (registry == null) {
            logger.info("Inject SchemaRegistry: "+ className);
            registry = (SchemaRegistry) Class.forName(className).getDeclaredConstructor().newInstance();
        }
        return registry;
    }

}
