package net.cloudburo.hexagon.demo.schemaregistry.impl.sandbox;

import net.cloudburo.hexagon.demo.schemaregistry.SchemaRegistry;
import org.apache.avro.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class SandboxRegistry extends SchemaRegistry {

    private static final Logger logger = LoggerFactory.getLogger(SandboxRegistry.class);

    private static HashMap<Long, String> registryStore = new HashMap<>();

    public void registerSchema(long fingerprint,  Schema schema) throws Exception {
        if (!registryStore.containsKey(fingerprint)) {
            String avroSchemaJSON = schema.toString(true);
            logger.info("Register Schema with fingerprint "+fingerprint+": "+schema.toString());
            registryStore.put(fingerprint,avroSchemaJSON);
        }
    }

    public Schema getSchema(long fingerprint) throws Exception {
        return new Schema.Parser().parse(registryStore.get(fingerprint));
    }
}
