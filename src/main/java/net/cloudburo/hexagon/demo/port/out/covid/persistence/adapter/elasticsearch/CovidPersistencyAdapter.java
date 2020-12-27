package net.cloudburo.hexagon.demo.port.out.covid.persistence.adapter.elasticsearch;

import net.cloudburo.hexagon.demo.domain.covid.CovidCase;
import net.cloudburo.hexagon.demo.port.out.covid.persistence.CovidPersistencePort;
import net.cloudburo.hexagon.demo.port.out.covid.persistence.CovidPersistencyPortConfig;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CovidPersistencyAdapter extends CovidPersistencePort {

    private static Logger logger = Logger.getLogger(CovidPersistencyAdapter.class);

    @Autowired
    private CovidPersistencyPortConfig config;
    @Autowired
    private ESPersistencyManager persistencyManager;

    boolean indexCheck = false;

    // TODO: EsType is obsolete
    private String type = "base";


    private void doIndexCheck() throws IOException {
        if (!indexCheck) {
            if (!persistencyManager.existsIndex(config.getIndex()))
                persistencyManager.createIndex(config.getIndex());
            indexCheck = true;
        }
    }

    protected ESPersistencyManager getPersistencyManager() throws Exception {
        doIndexCheck();
        return this.persistencyManager;
    }

    @Override
    public void addCovidRecord(CovidCase record) throws Exception {
        String id = record.getCountryTerritoryCode()+"-"+record.getReportingYear()+"-"+record.getReportingMonth()+"-"+ record.getReportingDay();
        String jsonDoc = serializeJSON(record);
        this.getPersistencyManager().createUpdateDocument(config.getIndex(), type, jsonDoc,id);
    }
}

