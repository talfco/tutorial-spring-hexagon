package net.cloudburo.hexagon.demo.schemaregistry;

import org.apache.avro.Schema;

public abstract class SchemaRegistry {

    public  abstract void registerSchema(long fingerprint, Schema schema) throws Exception;
    public  abstract Schema getSchema(long fingerprint) throws Exception;

}
