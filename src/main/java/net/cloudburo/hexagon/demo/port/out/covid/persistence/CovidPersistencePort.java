package net.cloudburo.hexagon.demo.port.out.covid.persistence;

import net.cloudburo.hexagon.demo.domain.covid.CovidCase;
import net.cloudburo.hexagon.demo.domain.covid.CovidCaseWeekly;

public  interface CovidPersistencePort  {

    public void persistDailyCovidRecord(CovidCase record) throws Exception;
    public void persistWeeklyCovidRecord(CovidCaseWeekly record) throws Exception;
}
