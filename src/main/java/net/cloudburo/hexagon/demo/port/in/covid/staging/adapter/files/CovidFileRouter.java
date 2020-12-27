package net.cloudburo.hexagon.demo.port.in.covid.staging.adapter.files;

import net.cloudburo.hexagon.demo.kernel.usecase.CovidUseCaseRepository;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.BindyType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CovidFileRouter extends RouteBuilder  {

    final CovidUseCaseRepository covidUseCaseRepository;

    @Value("${injector.staging.source}")
    private  String sourceFolder;
    @Value("${injector.staging.deadletter}")
    private  String deadLetterFolder;
    @Value("${injector.staging.split.target.fixedlen}")
    private  String destinationFolderFixedlen;
    @Value("${injector.staging.split.target.avro}")
    private  String destinationFolderAvro;
    @Value("${injector.staging.split.target.csv}")
    private  String destinationFolderCsv;
    @Value("${injector.staging.split.size}")
    private int splitSize;

    public CovidFileRouter(final CovidUseCaseRepository covidUseCaseRepository) {
        this.covidUseCaseRepository = covidUseCaseRepository;
    }

    @Override
    public void configure() throws Exception {
        from("file://" + sourceFolder + "?delete=true").routeId("file-splitter-route")
                .tracing()
                .choice()
                    .when(simple("${file:ext} == 'prn'"))
                        .to("direct:split-prn")
                        .log("Processed file ${file:name} will be moved into folder "+destinationFolderFixedlen)
                        .setHeader(Exchange.FILE_NAME, simple("${file:name.noext}-${date:now:yyyyMMddHHmmssSSS}.${file:ext}"))
                        .to("file://" + destinationFolderFixedlen)
                    .when(simple("${file:ext} == 'csv'"))
                        .log("CSV file ${file:name} will be splitted into folder "+destinationFolderCsv)
                        .to("file://" + destinationFolderCsv)
                    .when(simple("${file:ext} == 'avro'"))
                        .log("Avro file ${file:name} will be splitted into folder "+destinationFolderAvro)
                        .to("file://" + destinationFolderAvro)
                    .otherwise()
                        .log("Extension ${file:ext} cannot be processed, moving ${file:name} into folder "+deadLetterFolder)
                        .to("file://" + deadLetterFolder);
        from("direct:split-prn").routeId("split-prn-files-route")
                // We process one record entry after the other
                .log("Start transform to Domain Model ")
                .split().tokenize("\n").streaming()
                    .unmarshal()
                    .bindy(BindyType.Fixed, CovidCaseRecord.class)
                    .process(new Processor() {
                        public void process(Exchange exchange) throws Exception {
                            Message in = exchange.getIn();
                            CovidCaseRecord rec =(CovidCaseRecord) in.getBody();
                            covidUseCaseRepository.addCovidCase(rec.transformToDomainClass());
                        }
                    })
                .end()
                .log("Stop transform to Domain Model ");
        from("direct:split-csv").routeId("split-csv-files-route")
                .tracing()
                // TODO: split-csv route
                .log("Not implemented yet");
        from("direct:split-avro").routeId("split-avro-files-route")
                .tracing()
                // TODO: split-avro route
                .log("Not implemented yet");
    }

}
