package net.cloudburo.hexagon.demo.kernel;

import net.cloudburo.hexagon.demo.schemaregistry.SchemaRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public abstract class BaseUseCaseRepository {

    private static final Logger logger = LoggerFactory.getLogger(BaseUseCaseRepository.class);

    @Autowired
    private KernelConfig kernelConfig;
    @Autowired
    private ApplicationContext context;


    protected SchemaRegistry schemaRegistry;

    protected abstract void registerSchema() throws Exception;

    public SchemaRegistry getSchemaRegistry() throws Exception {
        if (schemaRegistry == null) {
            logger.info("Lookup SchemaRegistry Bean: "+kernelConfig.getSchemaRegistryBean());
            schemaRegistry = (SchemaRegistry)context.getBean(kernelConfig.getSchemaRegistryBean());
        }
        return schemaRegistry;
    }
}
