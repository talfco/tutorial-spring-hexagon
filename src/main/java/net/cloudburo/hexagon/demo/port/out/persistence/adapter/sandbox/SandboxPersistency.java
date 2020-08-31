package net.cloudburo.hexagon.demo.port.out.persistence.adapter.sandbox;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import net.cloudburo.hexagon.demo.domain.Header;
import net.cloudburo.hexagon.demo.domain.Ids;
import net.cloudburo.hexagon.demo.domain.User;
import net.cloudburo.hexagon.demo.port.out.persistence.PersistencePort;
import org.apache.avro.Schema;
import org.apache.avro.SchemaNormalization;

import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class SandboxPersistency extends PersistencePort {

    private static Logger logger = Logger.getLogger(SandboxPersistency.class);

    // For illustration purpose we persist the User DO as Avro JSON
    private static HashMap<String, String> cache = new HashMap<>();

    @Override
    public User createUser(User data) throws Exception {
        // Ok here we would store the Avro JSON Document
        Ids.Builder builder;
        // Ids section may be empty
        if (data.getIds()==null) {
            builder = Ids.newBuilder();
        } else {
            builder = Ids.newBuilder(data.getIds());
        }
        Ids ids = builder
                .setUid("12345678")
                .setDomain("8080")
                .build();

        // We inject the Avro fingerprint into the JSON Document, in a sense
        // we seal the document with its specific Schema, this will allow us
        // during retrieval of the document to detect the right schema which
        // can interpret the document (provided by the Schema registry)
        Header header = Header.newBuilder(data.getHeader())
                .setAvroFingerprint(fingerprint)
                .setLastUpdateTimestamp(java.lang.System.currentTimeMillis())
                .build();
        User user = User.newBuilder(data)
                .setIds(ids)
                .setHeader(header)
                .build();

        String jsonDoc = serializeJSON(user);
        logger.info("Persisting Avro-JSON "+jsonDoc);
        cache.put(user.getIds().getUid(), jsonDoc);
        return user;
    }

    @Override
    public User readUser(String id) throws Exception {
        String avroJsonDomainData = cache.get(id);
        // Retrieve the fingerprint, i.e. the Schema which was used to persist the JSON document
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(avroJsonDomainData);
        long docFingerprint = jsonElement.getAsJsonObject().getAsJsonObject("header").get("avroFingerprint").getAsLong();
        logger.info("Persisted Document has Avro Schema fingerprint: "+docFingerprint);
        User user;
        if (this.fingerprint == docFingerprint) {
            logger.info("Persisted Document has same Avro Schema fingerprint, go for normal Serialization process");
            user = deSerializeJSON(avroJsonDomainData);
        } else {
            logger.info("Persisted Document has another Avro Schema fingerprint, go for Schema Evolution Process");
            Schema schema = this.getSchema(docFingerprint);
            user = schemaEvolutionJSON(avroJsonDomainData,schema, User.getClassSchema());
        }
        return user;
    }

}
