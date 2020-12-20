package net.cloudburo.hexagon.demo.port.in.files.adapter.camel;


import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;

public class ContentBasedFileRouter extends RouteBuilder  {

    @Value("${fileStagingSourceFolder}")
    private  String sourceFolder;
    @Value("${fileStagingSplitFolderFixedLength}")
    private  String destinationFolderTxt;
    @Value("${fileStagingSplitFolderAvro}")
    private  String destinationFolderOther;

    @Override
    public void configure() throws Exception {
        from("file://" + sourceFolder + "?delete=true").choice().when(simple("${file:ext} == 'txt'")).to("file://" + destinationFolderTxt).otherwise().to("file://" + destinationFolderOther);
    }


}
