package net.cloudburo.hexagon.demo.port.in.covid.staging;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.file.GenericFileMessage;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;

public class MD5FileContentProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        FileInputStream fis = new FileInputStream(((File)exchange.getIn(GenericFileMessage.class).getGenericFile().getFile()));
        exchange.getIn().setHeader("md5Key", DigestUtils.md5Hex(fis));
        fis.close();
    }
}
