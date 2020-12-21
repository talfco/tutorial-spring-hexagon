package net.cloudburo.hexagon.demo.port.in.files.adapter.camel;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileSplitterRouter extends RouteBuilder  {

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

    @Override
    public void configure() throws Exception {
        from("file://" + sourceFolder + "?delete=true").routeId("file-splitter-route")
                .tracing()
                .choice()
                    .when(simple("${file:ext} == 'prn'"))
                        .log("Fixed Length file ${file:name} will be splitted into folder "+destinationFolderFixedlen)
                        //.split().tokenize("\n",1000).streaming()
                        .to("direct:split-prn")
                    .when(simple("${file:ext} == 'csv'"))
                        .log("CSV file ${file:name} will be splitted into folder "+destinationFolderCsv)
                        .to("file://" + destinationFolderCsv)
                    .when(simple("${file:ext} == 'avsc'"))
                        .log("Avro file ${file:name} will be splitted into folder "+destinationFolderCsv)
                        .to("file://" + destinationFolderAvro)
                    .otherwise()
                        .log("Extension ${file:ext} cannot be processed, moving ${file:name} into folder "+deadLetterFolder)
                        .to("file://" + deadLetterFolder);
        from("direct:split-prn")
                .tracing()
                .split().tokenize("\n",splitSize).streaming()
                .setHeader(Exchange.FILE_NAME, simple("${file:name.noext}-${date:now:yyyyMMddHHmmssSSS}.${file:ext}"))
                .to("file://" + destinationFolderFixedlen);
    }

}
