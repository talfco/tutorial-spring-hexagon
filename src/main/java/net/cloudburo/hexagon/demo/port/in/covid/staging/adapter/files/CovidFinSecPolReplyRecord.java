package net.cloudburo.hexagon.demo.port.in.covid.staging.adapter.files;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord( separator = "," )
public class CovidFinSecPolReplyRecord {

    @DataField(pos = 1)
    private int id;

    @DataField(pos = 2)
    private String countryName;

    @DataField(pos = 3)
    private String countryISO3;

    @DataField(pos = 4)
    private String incomeLevel;

    @DataField(pos = 5)
    private String authority;

    @DataField(pos = 6)
    private String level1PolicyMeasures;

    @DataField(pos = 7)
    private String level2PolicyMeasures;

    @DataField(pos = 8)
    private String level3PolicyMeasures;

    @DataField(pos = 9)
    private String detailsOfTheMeasure;

    @DataField(pos = 10)
    private String reference;

    @DataField(pos = 11)
    private String terminationDate;

    @DataField(pos = 12)
    private String modificationOfParentMeasure;

    @DataField(pos = 13)
    private String parentMeasure;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryISO3() {
        return countryISO3;
    }

    public void setCountryISO3(String countryISO3) {
        this.countryISO3 = countryISO3;
    }

    public String getIncomeLevel() {
        return incomeLevel;
    }

    public void setIncomeLevel(String incomeLevel) {
        this.incomeLevel = incomeLevel;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getLevel1PolicyMeasures() {
        return level1PolicyMeasures;
    }

    public void setLevel1PolicyMeasures(String level1PolicyMeasures) {
        this.level1PolicyMeasures = level1PolicyMeasures;
    }

    public String getLevel2PolicyMeasures() {
        return level2PolicyMeasures;
    }

    public void setLevel2PolicyMeasures(String level2PolicyMeasures) {
        this.level2PolicyMeasures = level2PolicyMeasures;
    }

    public String getLevel3PolicyMeasures() {
        return level3PolicyMeasures;
    }

    public void setLevel3PolicyMeasures(String level3PolicyMeasures) {
        this.level3PolicyMeasures = level3PolicyMeasures;
    }

    public String getDetailsOfTheMeasure() {
        return detailsOfTheMeasure;
    }

    public void setDetailsOfTheMeasure(String detailsOfTheMeasure) {
        this.detailsOfTheMeasure = detailsOfTheMeasure;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(String terminationDate) {
        this.terminationDate = terminationDate;
    }

    public String getModificationOfParentMeasure() {
        return modificationOfParentMeasure;
    }

    public void setModificationOfParentMeasure(String modificationOfParentMeasure) {
        this.modificationOfParentMeasure = modificationOfParentMeasure;
    }

    public String getParentMeasure() {
        return parentMeasure;
    }

    public void setParentMeasure(String parentMeasure) {
        this.parentMeasure = parentMeasure;
    }
}
