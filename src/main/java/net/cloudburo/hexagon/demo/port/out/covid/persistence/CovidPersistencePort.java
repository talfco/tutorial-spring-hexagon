package net.cloudburo.hexagon.demo.port.out.covid.persistence;

import net.cloudburo.hexagon.demo.domain.covid.CovidCase;

public  interface CovidPersistencePort  {

    public void persistDailyCovidRecord(CovidCase record) throws Exception;


}
