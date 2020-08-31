package net.cloudburo.hexagon.demo.schemaregistry.impl.inmemory;

import net.cloudburo.hexagon.demo.schemaregistry.SchemaRegistry;
import org.apache.avro.Schema;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.util.HashMap;

public class InMemoryRegistry extends SchemaRegistry {

    private static HashMap<Long,String> registry = new HashMap<>();
    private static Logger logger = Logger.getLogger(SchemaRegistry.class);

    public  long registerSchema(Schema schema)  throws IOException {
        long fingerprint = getSchemaFingerprint(schema);
        String schemaJSON = schema.toString(true);
        logger.info("Registering Schema with fingerprint '"+fingerprint+"': "+schemaJSON);
        registry.put(fingerprint,schemaJSON);
        return fingerprint;
    }

    public  Schema getSchema(long fingerprint) throws IOException {
        Schema schema = new Schema.Parser().parse(registry.get(fingerprint));
        return schema;
    };
}

