package net.cloudburo.hexagon.demo.port.out.covid.persistence;

import clb.covid.CovidCase;
import clb.covid.CovidCaseWeekly;

public  interface CovidPersistencePort  {

    public void persistDailyCovidRecord(CovidCase record) throws Exception;
    public void persistWeeklyCovidRecord(CovidCaseWeekly record) throws Exception;
}
