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
public class CovidFileRouter extends RouteBuilder  {

    private static final String sourcePostfix = "/covidcase";
    
    final CovidStagingPort covidStagingPort;
    
    @Autowired
    private CovidStagingPortConfig covidStagingPortConfig;

    public CovidFileRouter(final CovidUseCaseRepository covidUseCaseRepository) {
        this.covidStagingPort = covidUseCaseRepository;
    }

    @Override
    public void configure() throws Exception {
        from("file://" + covidStagingPortConfig.getSource()+ sourcePostfix + "?delete=true").routeId("covid-file-route")
                .tracing()
                .choice()
                    .when(simple("${file:ext} == 'prn'"))
                        .to("direct:split-prn")
                        .log("Processed file ${file:name} will be moved into folder "+covidStagingPortConfig.getTargetFixedLen())
                        .setHeader(Exchange.FILE_NAME, simple("${file:name.noext}-${date:now:yyyyMMddHHmmssSSS}.${file:ext}"))
                        .to("file://" + covidStagingPortConfig.getTargetFixedLen())
                    .when(simple("${file:ext} == 'csv'"))
                        .log("CSV file ${file:name} will be splitted into folder "+covidStagingPortConfig.getTargetCSV())
                        .to("file://" + covidStagingPortConfig.getTargetCSV())
                    .when(simple("${file:ext} == 'avro'"))
                        .log("Avro file ${file:name} will be splitted into folder "+covidStagingPortConfig.getTargetAvro())
                        .to("file://" + covidStagingPortConfig.getTargetAvro())
                    .otherwise()
                        .log("Extension ${file:ext} cannot be processed, moving ${file:name} into folder "+covidStagingPortConfig.getDeadletter())
                        .to("file://" + covidStagingPortConfig.getDeadletter());
        from("direct:split-prn").routeId("covid-prn-file-route")
                // We process one record entry after the other
                .log("Start transform to Domain Model ")
                .split().tokenize("\n").streaming()
                    .unmarshal()
                    .bindy(BindyType.Fixed, CovidCaseRecord.class)
                    .process(new Processor() {
                        public void process(Exchange exchange) throws Exception {
                            Message in = exchange.getIn();
                            CovidCaseRecord rec =(CovidCaseRecord) in.getBody();
                            covidStagingPort.addDailyCovidCases(rec.transformToDomainClass());
                        }
                    })
                .end()
                .log("Transform to Domain Model Completed ");
        from("direct:split-csv").routeId("covid-csv-file-route")
                .tracing()
                // TODO: split-csv route
                .log("Not implemented yet");
        from("direct:split-avro").routeId("covid-avro-file-route")
                .tracing()
                // TODO: split-avro route
                .log("Not implemented yet");
    }

}
