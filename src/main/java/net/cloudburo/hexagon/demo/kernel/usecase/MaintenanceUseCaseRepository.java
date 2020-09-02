package net.cloudburo.hexagon.demo.kernel.usecase;

import org.apache.avro.SchemaNormalization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

// The Inbound and Outbound Ports
import net.cloudburo.hexagon.demo.port.in.maintenance.MaintenancePort;
import net.cloudburo.hexagon.demo.port.out.persistence.PersistencePort;
// The Domain Data
import net.cloudburo.hexagon.demo.domain.User;
// The Schema Registry
import net.cloudburo.hexagon.demo.schemaregistry.SchemaRegistry;

@Repository
@Configuration
public class MaintenanceUseCaseRepository implements MaintenancePort {

    private static final Logger logger = LoggerFactory.getLogger(MaintenanceUseCaseRepository.class);
    private static SchemaRegistry registry;

    // We inject here different adapters
    @Value("${persistencePortAdapter}")
    private String persistencePortAdapterClassName;
    @Value("${schemaRegistry}")
    private String schemaRegistryClassName;

    private  PersistencePort persistencePort;
    private SchemaRegistry schemaRegistry;

    private PersistencePort getPersistencePort() throws Exception {
        if (persistencePort == null) {
            logger.info("Inject Maintenance Port with Adapter: "+ persistencePortAdapterClassName);
            persistencePort = (PersistencePort) Class.forName(persistencePortAdapterClassName).getDeclaredConstructor().newInstance();
            persistencePort.setRegistry(getSchemaRegistry());
        }
        return persistencePort;
    }

    public User readUser(String id) throws Exception {
        return this.getPersistencePort().readUser(id);
    }

    public User createUser(User user) throws Exception {
        return this.getPersistencePort().createUser(user);

    }

    public SchemaRegistry getSchemaRegistry() throws Exception{
        if (schemaRegistry == null) {
            logger.info("Inject SchemaRegistry: "+ schemaRegistryClassName);
            registry = (SchemaRegistry) Class.forName(schemaRegistryClassName).getDeclaredConstructor().newInstance();
            long fingerprint = SchemaNormalization.parsingFingerprint64(User.getClassSchema());
            registry.registerSchema(fingerprint,User.getClassSchema());
        }
        return registry;
    }
}
