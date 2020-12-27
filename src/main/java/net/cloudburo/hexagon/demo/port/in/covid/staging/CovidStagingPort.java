package net.cloudburo.hexagon.demo.port.in.covid.staging;

import net.cloudburo.hexagon.demo.domain.covid.CovidCase;

/*
A port interfaces understands the domain model data structure, which are passed in or retrieved
In our case Avro Schema based data structures.
The port will be implemented by a corresponding UseCaseRepository in the kernel.usecase package.
The adapter implementation will be wired with UseCaseRepository
 */
public interface CovidStagingPort {

    public void addCovidCase(CovidCase caseRecord) throws Exception;
}

