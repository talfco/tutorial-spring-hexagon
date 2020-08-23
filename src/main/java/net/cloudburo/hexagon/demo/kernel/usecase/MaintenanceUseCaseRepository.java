package net.cloudburo.hexagon.demo.kernel.usecase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

// Ports
import net.cloudburo.hexagon.demo.port.in.maintenance.MaintenancePort;
import net.cloudburo.hexagon.demo.port.out.persistence.PersistencePort;
// The Domain Data
import com.cloudburo.hexagon.demo.domain.EmailData;

@Repository
@Configuration
public class MaintenanceUseCaseRepository implements MaintenancePort {

    private static final Logger logger = LoggerFactory.getLogger(MaintenanceUseCaseRepository.class);

    @Value("${persistencePortAdapter}")
    private String persistencePortAdapter;

    private  PersistencePort persistencePort;


    private PersistencePort getPersistencePort() throws Exception {
        if (persistencePort == null) {
            logger.info("Inject Maintenance Port with Adapter: "+ persistencePortAdapter);
            persistencePort = (PersistencePort) Class.forName(persistencePortAdapter).getDeclaredConstructor().newInstance();
        }
        return persistencePort;
    }

    public EmailData readUser(String id) throws Exception {
        return this.getPersistencePort().readUser(id);
    }

    public EmailData createUser(EmailData emailData) throws Exception {
        return getPersistencePort().createUser(emailData);

    }
}
