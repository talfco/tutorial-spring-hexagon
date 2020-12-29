package net.cloudburo.hexagon.demo.port.in.covid.staging;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("injector.staging")
public class CovidStagingPortConfig {

    private String source;

    private String target;

    private String deadletter;

    private String targetFixedLen = target+"/fixedlen";

    private String targetCSV = target+"/csv";

    private String targetAvro = target+"/avro";

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
        return targetFixedLen;
    }

    public void setTargetFixedLen(String targetFixedLen) {
        this.targetFixedLen = targetFixedLen;
    }

    public String getTargetCSV() {
        return targetCSV;
    }

    public void setTargetCSV(String targetCSV) {
        this.targetCSV = targetCSV;
    }

    public String getTargetAvro() {
        return targetAvro;
    }

    public void setTargetAvro(String targetAvro) {
        this.targetAvro = targetAvro;
    }
}
