package net.cloudburo.hexagon.demo.port.in.covid.staging.adapter.files;

import clb.covid.CovidCaseWeekly;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import java.math.BigDecimal;
import java.util.StringTokenizer;

// https://stackoverflow.com/questions/39503312/apache-camel-2-17-3-exception-unmarshalling-csv-stream-with-bindy
@CsvRecord( separator = "," )
public class WeeklyCovidCaseRecord {

    @DataField(pos = 1)
    private String country;

    @DataField(pos = 2)
    private String countryCode;

    @DataField(pos = 3)
    private String continent;

    @DataField(pos = 4)
    private long population;

    @DataField(pos = 5)
    private String  indicator;

    @DataField(pos = 6)
    private long  weeklyCount;

    @DataField(pos = 7)
    private String  year_week;

    @DataField(pos = 8, groupingSeparator = ".",precision = 19)
    private BigDecimal rate14Day;

    @DataField(pos = 9)
    private long cumulativeCount;

    @DataField(pos = 10)
    private String source;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public String getIndicator() {
        return indicator;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

    public long getWeeklyCount() {
        return weeklyCount;
    }

    public void setWeeklyCount(long weeklyCount) {
        this.weeklyCount = weeklyCount;
    }

    public String getYear_week() {
        return year_week;
    }

    public void setYear_week(String year_week) {
        this.year_week = year_week;
    }

    public BigDecimal getRate14Day() {
        return rate14Day;
    }

    public void setRate14Day(BigDecimal rate14Day) {
        this.rate14Day = rate14Day;
    }

    public long getCumulativeCount() {
        return cumulativeCount;
    }

    public void setCumulativeCount(long cumulativeCount) {
        this.cumulativeCount = cumulativeCount;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public CovidCaseWeekly transformToDomainClass()
    {
        float rate14Day = 0;
        if (this.rate14Day != null)
            rate14Day = this.rate14Day.floatValue();
        StringTokenizer tok = new StringTokenizer(this.year_week,"-");
        CovidCaseWeekly caseweekly = CovidCaseWeekly.newBuilder()
            .setReportingYear(Integer.valueOf(tok.nextToken()))
            .setReportingWeek(Integer.valueOf(tok.nextToken()))
            .setCountriesAndTerritories(this.country)
            .setCountryCode(this.countryCode)
            .setContinent(this.continent)
            .setCumNum14daysPer1000(rate14Day)
            .setPopulation2019(this.population)
            .setIndicator(this.indicator)
            .setWeeklyCount(this.weeklyCount)
            .setCumulativeCount(this.cumulativeCount)
            .setSource(this.source)
            .build();
        return caseweekly;
    }
}
