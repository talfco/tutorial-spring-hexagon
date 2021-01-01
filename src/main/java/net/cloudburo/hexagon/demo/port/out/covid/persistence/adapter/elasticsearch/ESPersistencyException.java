package net.cloudburo.hexagon.demo.port.out.covid.persistence.adapter.elasticsearch;

public class ESPersistencyException extends Exception {

    int errorCode;

    public ESPersistencyException(int code,String errorMessage) {
        super(errorMessage);
        this.errorCode = code;
    }
    public ESPersistencyException(String errorMessage, Throwable err) {
        super(errorMessage,err);
    }
}
