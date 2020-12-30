package net.cloudburo.hexagon.demo.port.in.covid.staging.adapter.files;

import net.cloudburo.hexagon.demo.kernel.covid.CovidUseCaseRepository;
import net.cloudburo.hexagon.demo.port.in.covid.staging.CovidStagingPort;
import net.cloudburo.hexagon.demo.port.in.covid.staging.CovidStagingPortConfig;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.BindyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WeeklyCovidFileRouter extends RouteBuilder {

    private static final String sourcePostfix = "/covidcaseweekly";

    final CovidStagingPort covidStagingPort;

    @Autowired
    CovidStagingPortConfig covidStagingPortConfig;

    public WeeklyCovidFileRouter(final CovidUseCaseRepository covidUseCaseRepository) {
        this.covidStagingPort = covidUseCaseRepository;
    }

    @Override
    public void configure() throws Exception {
        from("file://" + covidStagingPortConfig.getSource()+ sourcePostfix + "?delete=true").routeId("covidweekly-file-route")
            // We process one record entry after the other
            .log("Start transform of CovidCaseWeekly to Domain Model ")
            //.split(body().tokenize("\n",1,true)).streaming()
            .split().tokenize("\n",1,true).streaming()
                .unmarshal()
                .bindy(BindyType.Csv, WeeklyCovidCaseRecord.class)
                .process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        Message in = exchange.getIn();
                        WeeklyCovidCaseRecord rec =(WeeklyCovidCaseRecord) in.getBody();
                        covidStagingPort.addWeeklyCovidCases(rec.transformToDomainClass());
                    }
                })
            .end()
            .log("Transform to Domain Model Completed ");
    }

}
