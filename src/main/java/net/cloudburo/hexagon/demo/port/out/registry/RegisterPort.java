package net.cloudburo.hexagon.demo.port.out.registry;

import org.apache.avro.Schema;

public interface RegisterPort {
    public void registerSchema(long fingerprint,  Schema schema) throws Exception;
    public Schema getSchema(long fingerprint) throws Exception;
}
