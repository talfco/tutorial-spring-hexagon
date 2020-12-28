package net.cloudburo.hexagon.demo.kernel.user;

import net.cloudburo.hexagon.demo.kernel.BaseUseCaseRepository;
import org.apache.avro.SchemaNormalization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

// The Inbound and Outbound Ports
import net.cloudburo.hexagon.demo.port.in.user.maintain.UserMaintainPort;
import net.cloudburo.hexagon.demo.port.out.user.persistence.UserPersistencePort;
// The Domain Data
import net.cloudburo.hexagon.demo.domain.User;
// The Schema Registry
import net.cloudburo.hexagon.demo.schemaregistry.SchemaRegistry;

@Repository
@Configuration
public class UserUseCaseRepository extends BaseUseCaseRepository implements UserMaintainPort {

    private static final Logger logger = LoggerFactory.getLogger(UserUseCaseRepository.class);
    private static SchemaRegistry registry;

    // We wire here the persistency adapter
    @Value("${injector.persistency.user.portadapter}")
    private String persistencePortAdapterClassName;

    private UserPersistencePort persistencePort;

    private UserPersistencePort getPersistencePort() throws Exception {
        if (persistencePort == null) {
            logger.info("Inject Maintenance Port with Adapter: "+ persistencePortAdapterClassName);
            persistencePort = (UserPersistencePort) Class.forName(persistencePortAdapterClassName).getDeclaredConstructor().newInstance();
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

    protected void registerSchema() throws Exception {
        long fingerprint = SchemaNormalization.parsingFingerprint64(User.getClassSchema());
        this.schemaRegistry.registerSchema(fingerprint,User.getClassSchema());
    }

}
