package net.cloudburo.hexagon.demo.schemaregistry.impl.elasticsearch;

import com.google.gson.JsonObject;
import net.cloudburo.hexagon.demo.port.out.persistence.adapter.elasticsearch.ESPersistencyManager;
import net.cloudburo.hexagon.demo.schemaregistry.SchemaRegistry;
import org.apache.avro.Schema;

import java.io.IOException;

public class ElasticSearchSchemaRegistry extends SchemaRegistry {

    private static final String esIndex = "avroschema";

    ESPersistencyManager esManager;

    public void setESPersistencyManager(ESPersistencyManager esManager) {
        this.esManager = esManager;
    }

    public void registerSchema(long fingerprint, Schema schema) throws IOException {
        String avroJSONSchema = schema.toString(true);
        String doc = "{";
        doc+= "\"namespace\":"+"\""+schema.getNamespace()+"."+schema.getName()+"\",";
        doc+= "\"avroschema\":"+avroJSONSchema;
        doc+= "}";
        esManager.createUpdateDocument(esIndex,"schema",doc,Long.valueOf(fingerprint).toString());
    }

    public  Schema getSchema(long fingerprint) throws IOException {
        JsonObject jsonDoc = esManager.readDocumentByIdAsObject(esIndex, Long.valueOf(fingerprint).toString());
        String json = jsonDoc.get("_source").getAsJsonObject().get("avroschema").toString();
        Schema schema = new Schema.Parser().parse(json);
        return schema;
    }

}
