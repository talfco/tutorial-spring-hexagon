package net.cloudburo.hexagon.demo.kernel.covid;

// Domain Classes
import net.cloudburo.hexagon.demo.domain.covid.CovidCase;
import net.cloudburo.hexagon.demo.domain.covid.CovidCaseWeekly;
import net.cloudburo.hexagon.demo.kernel.BaseUseCaseRepository;
import net.cloudburo.hexagon.demo.kernel.KernelConfig;
import net.cloudburo.hexagon.demo.port.in.covid.staging.CovidStagingPort;
import net.cloudburo.hexagon.demo.port.out.covid.persistence.CovidPersistencePort;
import net.cloudburo.hexagon.demo.port.out.covid.persistence.CovidPersistencyPortConfig;
import net.cloudburo.hexagon.demo.schemaregistry.SchemaRegistry;
import org.apache.avro.SchemaNormalization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

// Use Case Ports
// Third part libraries

@Repository
@Configuration
public class CovidUseCaseRepository extends BaseUseCaseRepository implements CovidStagingPort {

    private static final Logger logger = LoggerFactory.getLogger(CovidUseCaseRepository.class);

    @Autowired
    private ApplicationContext context;
    @Autowired
    private CovidPersistencyPortConfig portConfig;
    @Autowired
    private KernelConfig kernelConfig;

    private SchemaRegistry schemaRegistry;

    private CovidPersistencePort persistencePort;

    private CovidPersistencePort getPersistencePort() throws Exception {
        if (persistencePort == null) {
            logger.info("Establish persistency port adapter "+portConfig.getPortAdapterBean());
            // We do a bean lookup, allowing to change the port adapter via configuration
            persistencePort = (CovidPersistencePort)context.getBean(portConfig.getPortAdapterBean());
            schemaRegistry = (SchemaRegistry)context.getBean(kernelConfig.getSchemaRegistryBean());
            registerSchema();
        }
        return persistencePort;
    }

    protected void registerSchema() throws Exception {
        long fingerprint = SchemaNormalization.parsingFingerprint64(CovidCase.getClassSchema());
        logger.debug("Going to register domain object "+CovidCase.class.getCanonicalName()+" with fingerprint "+fingerprint);
        this.schemaRegistry.registerSchema(fingerprint,CovidCase.getClassSchema());
    }

    @Override
    public void addDailyCovidCases(CovidCase caseRecord) throws Exception {
        getPersistencePort().persistDailyCovidRecord(caseRecord);
    }

    @Override
    public void addWeeklyCovidCases(CovidCaseWeekly caseRecord) throws  Exception {
        getPersistencePort().persistWeeklyCovidRecord(caseRecord);
    }
}
