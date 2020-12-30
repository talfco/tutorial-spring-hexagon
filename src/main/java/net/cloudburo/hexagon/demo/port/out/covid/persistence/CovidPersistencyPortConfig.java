package net.cloudburo.hexagon.demo.port.out.covid.persistence;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("injector.persistency.covid")
public class CovidPersistencyPortConfig {

    private String portAdapterBean;
    private String indexCovid;
    private String indexCovidWeekly;
    private String indexCovidFinPolResp;

    public String getIndexCovidWeekly() {
        return indexCovidWeekly;
    }

    public void setIndexCovidWeekly(String indexCovidWeekly) {
        this.indexCovidWeekly = indexCovidWeekly;
    }

    public String getIndexCovidFinPolResp() {
        return indexCovidFinPolResp;
    }

    public void setIndexCovidFinPolResp(String indexCovidFinPolResp) {
        this.indexCovidFinPolResp = indexCovidFinPolResp;
    }

    public String getPortAdapterBean() {
        return portAdapterBean;
    }

    public void setPortAdapterBean(String portAdapterBean) {
        this.portAdapterBean = portAdapterBean;
    }

    public String getIndexCovid() {
        return indexCovid;
    }

    public void setIndexCovid(String indexCovid) {
        this.indexCovid = indexCovid;
    }
}

