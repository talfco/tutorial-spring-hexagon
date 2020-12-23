package net.cloudburo.hexagon.demo.port.in.staging.covid;

import net.cloudburo.hexagon.demo.domain.covid.CovidCase;

public interface CovidStagingPort {

    public void addCovidCase(CovidCase caseRecord) throws Exception;
}

