package net.cloudburo.hexagon.demo.kernel;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("injector.kernel")
public class KernelConfig {

    private String schemaRegistryBean;
    private String schemaRegistryIndex;
    private String domainCovidCaseId;
    private String domainCovidWeeklyCaseId;
    private String domainCovidFinancialPolicyId;

    public String getDomainCovidCaseId() {
        return domainCovidCaseId;
    }

    public void setDomainCovidCaseId(String domainCovidCaseId) {
        this.domainCovidCaseId = domainCovidCaseId;
    }

    public String getDomainCovidWeeklyCaseId() {
        return domainCovidWeeklyCaseId;
    }

    public void setDomainCovidWeeklyCaseId(String domainCovidWeeklyCaseId) {
        this.domainCovidWeeklyCaseId = domainCovidWeeklyCaseId;
    }

    public String getDomainCovidFinancialPolicyId() {
        return domainCovidFinancialPolicyId;
    }

    public void setDomainCovidFinancialPolicyId(String domainCovidFinancialPolicyId) {
        this.domainCovidFinancialPolicyId = domainCovidFinancialPolicyId;
    }

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
