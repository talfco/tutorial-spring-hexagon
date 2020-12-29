package net.cloudburo.hexagon.demo.port.in.covid.staging.adapter.files;

import net.cloudburo.hexagon.demo.domain.covid.CovidCaseWeekly;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import java.math.BigDecimal;
import java.util.StringTokenizer;

@CsvRecord( separator = "," )
public class WeeklyCovidCaseRecord {

    @DataField(pos = 1)
    private String country;

    @DataField(pos = 2)
    private String countryCode;

    @DataField(pos = 3)
    private String continent;

    @DataField(pos = 4)
    private int population;

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
            .build();
        return caseweekly;
    }
}
