package net.cloudburo.hexagon.demo.port.out.covid.persistence.adapter.elasticsearch;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("injector.persistence.elasticsearch")
public class ESPersistencyConfig {

    private String esurl;
    private String username;
    private String password;

    public String getEsurl() {
        return esurl;
    }

    public void setEsurl(String esurl) {
        this.esurl = esurl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
