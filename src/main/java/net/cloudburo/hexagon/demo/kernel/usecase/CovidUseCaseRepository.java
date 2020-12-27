package net.cloudburo.hexagon.demo.kernel.usecase;

// Domain Classes
import net.cloudburo.hexagon.demo.domain.covid.CovidCase;
// Use Case Ports
import net.cloudburo.hexagon.demo.port.in.covid.staging.CovidStagingPort;
import net.cloudburo.hexagon.demo.port.out.covid.persistence.CovidPersistencePort;
import net.cloudburo.hexagon.demo.port.out.covid.persistence.CovidPersistencyPortConfig;
// Third part libraries
import org.apache.avro.SchemaNormalization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

@Repository
@Configuration
public class CovidUseCaseRepository extends BaseUseCaseRepository implements CovidStagingPort {
    private static final Logger logger = LoggerFactory.getLogger(CovidUseCaseRepository.class);

    @Autowired
    private ApplicationContext context;
    @Autowired
    private CovidPersistencyPortConfig portConfig;

    private CovidPersistencePort persistencePort;

    private CovidPersistencePort getPersistencePort() throws Exception {
        if (persistencePort == null) {
            logger.info("Establish persistency port adapter "+portConfig.getPortAdapter());
            persistencePort = (CovidPersistencePort)context.getBean(portConfig.getPortAdapter());
            persistencePort.setRegistry(getSchemaRegistry());
        }
        return persistencePort;
    }

    protected void registerSchema() throws Exception {
        long fingerprint = SchemaNormalization.parsingFingerprint64(CovidCase.getClassSchema());
        logger.debug("Going to register domain object "+CovidCase.class.getCanonicalName()+" with fingerprint "+fingerprint);
        this.schemaRegistry.registerSchema(fingerprint,CovidCase.getClassSchema());
    }

    @Override
    public void addCovidCase(CovidCase caseRecord) throws Exception {
        getPersistencePort().addCovidRecord(caseRecord);
    }
}
