package net.cloudburo.hexagon.demo.port.out.covid.persistence;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("injector.persistency.covid")
public class CovidPersistencyPortConfig {

    private String portAdapter;
    private String index;

    public String getPortAdapter() {
        return portAdapter;
    }

    public void setPortAdapter(String portAdapter) {
        this.portAdapter = portAdapter;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}

