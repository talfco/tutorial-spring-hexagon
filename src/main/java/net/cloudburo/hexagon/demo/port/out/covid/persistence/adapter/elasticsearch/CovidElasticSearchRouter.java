package net.cloudburo.hexagon.demo.port.out.covid.persistence.adapter.elasticsearch;

import clb.covid.CovidCase;
import clb.covid.CovidCaseWeekly;
import clb.infra.Header;
import net.cloudburo.hexagon.demo.port.out.covid.persistence.CovidPersistencePort;
import net.cloudburo.hexagon.demo.port.out.covid.persistence.CovidPersistencyPortConfig;
import net.cloudburo.hexagon.demo.port.out.covid.persistence.CovidSerializer;
import org.apache.avro.SchemaNormalization;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CovidElasticSearchRouter implements CovidPersistencePort {

    private static Logger logger = Logger.getLogger(CovidElasticSearchRouter.class);

    protected long fingerprint = SchemaNormalization.parsingFingerprint64(CovidCase.getClassSchema());

    @Autowired
    private CovidPersistencyPortConfig config;
    @Autowired
    private ESPersistencyManager persistencyManager;

    boolean indexCheck = false;

    // TODO: EsType is obsolete
    private String type = "base";


    private void doIndexCheck() throws IOException {
        if (!indexCheck) {
            if (!persistencyManager.existsIndex(config.getIndexCovid()))
                persistencyManager.createIndex(config.getIndexCovid());
            indexCheck = true;
        }
    }

    protected ESPersistencyManager getPersistencyManager() throws Exception {
        doIndexCheck();
        return this.persistencyManager;
    }

    @Override
    public void persistDailyCovidRecord(CovidCase record) throws Exception {
        String id = record.getCountryTerritoryCode()+"-"+record.getReportingYear()+"-"+record.getReportingMonth()+"-"+ record.getReportingDay();

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
        this.getPersistencyManager().createUpdateDocument(config.getIndexCovid(), jsonDoc,id);
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
        this.getPersistencyManager().createUpdateDocument(config.getIndexCovidWeekly(), jsonDoc,id);
    }
}

