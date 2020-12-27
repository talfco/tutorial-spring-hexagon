package net.cloudburo.hexagon.demo.kernel.usecase;

import net.cloudburo.hexagon.demo.schemaregistry.SchemaRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public abstract class BaseUseCaseRepository {

    private static final Logger logger = LoggerFactory.getLogger(BaseUseCaseRepository.class);

    @Value("${injector.persistency.schemaregistry}")
    private String schemaRegistryAdapterClassName;

    protected SchemaRegistry schemaRegistry;

    protected abstract void registerSchema() throws Exception;

    public SchemaRegistry getSchemaRegistry() throws Exception {
        if (schemaRegistry == null) {
            logger.info("Inject SchemaRegistry: " + schemaRegistryAdapterClassName);
            schemaRegistry = (SchemaRegistry) Class.forName(schemaRegistryAdapterClassName).getDeclaredConstructor().newInstance();
            registerSchema();
        }
        return schemaRegistry;
    }
}
