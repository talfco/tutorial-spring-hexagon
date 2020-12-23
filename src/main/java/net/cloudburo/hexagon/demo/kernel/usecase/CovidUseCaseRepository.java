package net.cloudburo.hexagon.demo.kernel.usecase;

import net.cloudburo.hexagon.demo.domain.covid.CovidCase;
import net.cloudburo.hexagon.demo.port.in.staging.covid.CovidStagingPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

@Repository
@Configuration
public class CovidUseCaseRepository implements CovidStagingPort {

    private static final Logger logger = LoggerFactory.getLogger(CovidUseCaseRepository.class);

    @Override
    public void addCovidCase(CovidCase caseRecord) throws Exception {
        logger.debug("Add Covid Case for: "+caseRecord.getReportingDate());
    }
}
