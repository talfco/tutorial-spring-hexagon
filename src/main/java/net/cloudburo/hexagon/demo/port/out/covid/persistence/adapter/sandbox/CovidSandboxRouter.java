package net.cloudburo.hexagon.demo.port.out.covid.persistence.adapter.sandbox;

import net.cloudburo.hexagon.demo.domain.covid.CovidCase;
import net.cloudburo.hexagon.demo.domain.covid.CovidCaseWeekly;
import net.cloudburo.hexagon.demo.domain.covid.Header;
import net.cloudburo.hexagon.demo.port.out.covid.persistence.CovidPersistencePort;
import net.cloudburo.hexagon.demo.port.out.covid.persistence.CovidSerializer;
import org.apache.avro.SchemaNormalization;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class CovidSandboxRouter  implements CovidPersistencePort  {

    private static Logger logger = Logger.getLogger(CovidSandboxRouter.class);

    protected long fingerprint = SchemaNormalization.parsingFingerprint64(CovidCase.getClassSchema());

    // For illustration purpose we persist the User Data Object as Avro JSON
    private static HashMap<String, String> cache = new HashMap<>();

    @Override
    public void persistDailyCovidRecord(CovidCase record) throws Exception {
        String id = record.getCountryTerritoryCode() + "-" + record.getReportingYear() + "-" + record.getReportingMonth() + "-" + record.getReportingDay();
        logger.info("Added Covid Case record "+id+" to in-memory cache");
        // We inject the Avro fingerprint into the JSON Document, in a sense
        // we seal the document with its specific Schema, this will allow us
        // during retrieval of the document to detect the right schema which
        // can interpret the document (provided by the Schema registry)
        Header header = Header.newBuilder()
                .setAvroFingerprint(fingerprint)
                .setLastUpdateTimestamp(java.lang.System.currentTimeMillis())
                .build();

        CovidCase updRecord = CovidCase.newBuilder(record)
                .setHeader(header)
                .build();

        String jsonDoc = CovidSerializer.serializeJSON(updRecord);
        logger.info("Adding Daily Record: "+id);
        cache.put(id, jsonDoc);
    }

    @Override
    public void persistWeeklyCovidRecord(CovidCaseWeekly record) throws Exception {
        String id = record.getReportingYear()+"-"+record.getReportingWeek()+"-"+record.getIndicator()+"-"
                +record.getCountriesAndTerritories().replaceAll("\\s+","");
        Header header = Header.newBuilder()
                .setAvroFingerprint(fingerprint)
                .setLastUpdateTimestamp(java.lang.System.currentTimeMillis())
                .build();
        CovidCaseWeekly updRecord = CovidCaseWeekly.newBuilder(record)
                .setHeader(header)
                .build();

        String jsonDoc = CovidSerializer.serializeJSON(updRecord);
        logger.info("Adding Weekly Record: "+id);
        cache.put(id, jsonDoc);
    }
}
