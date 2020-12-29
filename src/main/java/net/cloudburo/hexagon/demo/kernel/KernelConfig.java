package net.cloudburo.hexagon.demo.kernel;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("injector.kernel")
public class KernelConfig {

    private String schemaRegistryBean;
    private String schemaRegistryIndex;


    public String getSchemaRegistryBean() {
        return schemaRegistryBean;
    }

    public void setSchemaRegistryBean(String schemaRegistryBean) {
        this.schemaRegistryBean = schemaRegistryBean;
    }

    public String getSchemaRegistryIndex() {
        return schemaRegistryIndex;
    }

    public void setSchemaRegistryIndex(String schemaRegistryIndex) {
        this.schemaRegistryIndex = schemaRegistryIndex;
    }
}
