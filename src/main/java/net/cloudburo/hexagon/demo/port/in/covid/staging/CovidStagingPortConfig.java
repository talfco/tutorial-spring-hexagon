package net.cloudburo.hexagon.demo.port.in.covid.staging;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("injector.staging")
public class CovidStagingPortConfig {

    private String source;

    private String target;

    private String deadletter;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getDeadletter() {
        return deadletter;
    }

    public void setDeadletter(String deadletter) {
        this.deadletter = deadletter;
    }

    public String getTargetFixedLen() {
        return target+"/fixedlen";
    }

    public String getTargetCSV() {
        return target+"/scv";
    }

    public String getTargetAvro() {
        return target+"/avro";
    }

}
