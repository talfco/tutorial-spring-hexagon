package net.cloudburo.hexagon.demo.port.in.covid.staging.adapter.files;

import net.cloudburo.hexagon.demo.kernel.KernelConfig;
import net.cloudburo.hexagon.demo.kernel.covid.CovidUseCaseRepository;
import net.cloudburo.hexagon.demo.port.in.covid.staging.CovidStagingPort;
import net.cloudburo.hexagon.demo.port.in.covid.staging.CovidStagingPortConfig;
import net.cloudburo.hexagon.demo.port.in.covid.staging.FailureProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.BindyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WeeklyCovidFileRouter extends RouteBuilder {


    final CovidStagingPort covidStagingPort;

    @Autowired
    CovidStagingPortConfig covidStagingPortConfig;
    @Autowired
    KernelConfig kernelConfig;

    public WeeklyCovidFileRouter(final CovidUseCaseRepository covidUseCaseRepository) {
        this.covidStagingPort = covidUseCaseRepository;
    }

    @Override
    public void configure() throws Exception {
        //errorHandler(deadLetterChannel("log:dead?level=ERROR").useOriginalMessage().onPrepareFailure(new FailureProcessor()));
        errorHandler(deadLetterChannel("direct:dlStagingFileRoute").useOriginalMessage().onPrepareFailure(new FailureProcessor()));
        String useCaseId = kernelConfig.getDomainCovidWeeklyCaseId();
        from("file://" + covidStagingPortConfig.getSource()+ "/" + useCaseId + "?delete=true").routeId(useCaseId+"-file-route")
            .log("Start transform to Domain Model: ${date:now:yyyy-MM-dd-HH:mm:ss}")
            .setHeader("usecase",constant(useCaseId))
            // We process one record entry after the other
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
            .log("Transform to Domain Model Completed: ${date:now:yyyy-MM-dd-HH:mm:ss}")
            .to("direct://processedFileTransfer");
            /*
            .log("Processed file ${file:name} will be moved into folder "+covidStagingPortConfig.getTarget()+"/"+useCaseId)
            .setHeader(Exchange.FILE_NAME, simple("${file:name.noext}-${exchangeId}.${file:ext}"))
            .to("file://" + covidStagingPortConfig.getTarget()+"/"+useCaseId)
            // Let's calculate the MD5 Idempotent key of the content
            .process(new MD5FileContentProcessor())
            .setHeader(Exchange.FILE_NAME, simple("${file:name.noext}.md5"))
            .log("MD5 Key: ${header.md5key}")
            .setBody(simple("${header.md5key}"))
            .to("file://" + covidStagingPortConfig.getTarget()+"/"+useCaseId);

             */
    }

}
