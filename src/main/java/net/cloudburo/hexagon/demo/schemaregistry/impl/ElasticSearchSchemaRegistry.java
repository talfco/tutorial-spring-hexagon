package net.cloudburo.hexagon.demo.schemaregistry.impl;

import com.google.gson.JsonObject;
import net.cloudburo.hexagon.demo.kernel.KernelConfig;
import net.cloudburo.hexagon.demo.port.out.covid.persistence.adapter.elasticsearch.ESPersistencyManager;
import net.cloudburo.hexagon.demo.schemaregistry.SchemaRegistry;
import org.apache.avro.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ElasticSearchSchemaRegistry extends SchemaRegistry {

    @Autowired
    private KernelConfig kernelConfig;
    @Autowired
    private ESPersistencyManager esManager;

    public void registerSchema(long fingerprint, Schema schema) throws IOException {
        String avroJSONSchema = schema.toString(true);
        String doc = "{";
        doc+= "\"namespace\":"+"\""+schema.getNamespace()+"."+schema.getName()+"\",";
        doc+= "\"avroschema\":"+avroJSONSchema;
        doc+= "}";
        esManager.createUpdateDocument(kernelConfig.getSchemaRegistryIndex(),doc,Long.valueOf(fingerprint).toString());
    }

    public Schema getSchema(long fingerprint) throws IOException {
        JsonObject jsonDoc = esManager.readDocumentByIdAsObject(kernelConfig.getSchemaRegistryIndex(), Long.valueOf(fingerprint).toString());
        String json = jsonDoc.get("_source").getAsJsonObject().get("avroschema").toString();
        Schema schema = new Schema.Parser().parse(json);
        return schema;
    }
}
