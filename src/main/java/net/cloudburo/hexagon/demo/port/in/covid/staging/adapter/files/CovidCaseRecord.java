package net.cloudburo.hexagon.demo.port.in.covid.staging.adapter.files;

import net.cloudburo.hexagon.demo.domain.covid.CovidCase;

import org.apache.camel.dataformat.bindy.annotation.DataField;
import org.apache.camel.dataformat.bindy.annotation.FixedLengthRecord;

import java.math.BigDecimal;
import java.util.Date;

@FixedLengthRecord(length=195, paddingChar=' ',  skipHeader = true, ignoreMissingChars = true)
public class CovidCaseRecord {

    @DataField(pos = 1, length=15, pattern = "dd/MM/yyyy", trim = true)
    private Date recordDate;

    @DataField(pos = 2, length=15, trim = true)
    private int day;

    @DataField(pos = 3, length=15,trim = true)
    private int month;

    @DataField(pos = 4, length=15, trim = true)
    private int year;

    @DataField(pos = 5, length=15, trim = true)
    private int cases;

    @DataField(pos = 6, length=15, trim = true)
    private int deaths;

    @DataField(pos = 7, length=29, trim = true, align = "L")
    private String countriesAndTerritories;

    @DataField(pos = 8, length=15, trim = true, align = "L")
    private String geoId;

    @DataField(pos = 9, length=15, trim = true, align = "L")
    private String countryTerritoriesCode;

    @DataField(pos = 10, length=15, trim = true)
    private int population;

    @DataField(pos = 11, length=15, align = "L", trim = true)
    private String continent;

    @DataField(pos = 12, groupingSeparator = ".",precision = 8, length=15, trim = true)
    private BigDecimal cumCases;

    public CovidCase transformToDomainClass() {
        float cumCases = 0;
        if (this.cumCases != null)
            cumCases = this.cumCases.floatValue();
       CovidCase covidCase = CovidCase.newBuilder()
               .setReportingDate(this.recordDate.getTime())
               .setReportingYear(this.year)
               .setReportingMonth(this.month)
               .setReportingDay(this.day)
               .setCases(this.cases)
               .setDeaths(this.deaths)
               .setCountriesAndTerritories(this.countriesAndTerritories)
               .setGeoId(this.geoId)
               .setCountryTerritoryCode(this.countryTerritoriesCode)
               .setPopulation2019(this.population)
               .setContinent(this.continent)
               .setCumNum14daysPer1000(cumCases)
               .build();
       return covidCase;

    }

}
