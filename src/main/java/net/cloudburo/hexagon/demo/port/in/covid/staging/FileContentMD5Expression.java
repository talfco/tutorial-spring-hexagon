package net.cloudburo.hexagon.demo.port.in.covid.staging;

import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.component.file.GenericFileMessage;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/*
Refer to: https://stackoverflow.com/questions/54657032/md5-as-idempotentkey-in-apache-camel
 */
@Component
class FileContentMD5Expression implements Expression {
    @Override
    public <T> T evaluate(Exchange exchange, Class<T> type) {
        if (type != String.class){
            throw new IllegalArgumentException("This is String only expression");
        }
        try (FileInputStream fis = new FileInputStream(((File)exchange.getIn(GenericFileMessage.class).getGenericFile().getFile()))) {
            return type.cast(DigestUtils.md5Hex(fis));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
