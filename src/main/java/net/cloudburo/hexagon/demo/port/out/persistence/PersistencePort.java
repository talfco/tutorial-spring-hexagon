package net.cloudburo.hexagon.demo.port.out.persistence;

import net.cloudburo.hexagon.demo.domain.User;
import net.cloudburo.hexagon.demo.schemaregistry.SchemaRegistry;
import  org.apache.avro.Schema;
import org.apache.avro.SchemaNormalization;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.ByteArrayOutputStream;

public abstract class PersistencePort {

    protected SchemaRegistry registry;

    // This PersistencePort is managing Avro Schema with this fingerprint
    // In real life it is possible that the PersistencePort was storing with an earlier version
    // of a schema (other fingerprint).
    protected long fingerprint = SchemaNormalization.parsingFingerprint64(User.getClassSchema());

    public abstract  User createUser(User data) throws Exception;
    public abstract User readUser(String id) throws Exception;

    public void setRegistry(SchemaRegistry registry) {
        this.registry = registry;
    }

    public Schema getSchema(long id) throws Exception {
        return this.registry.getSchema(id);
    }

    public String serializeJSON(User request) throws Exception {
        DatumWriter<User> writer = new SpecificDatumWriter<>(User.class);
        byte[] data = new byte[0];
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Encoder jsonEncoder = null;
        jsonEncoder = EncoderFactory.get().jsonEncoder(User.getClassSchema(), stream);
        writer.write(request, jsonEncoder);
        jsonEncoder.flush();
        data = stream.toByteArray();
        return new String(data);
    }

    public User deSerializeJSON(String data) throws Exception {
        DatumReader<User> reader = new SpecificDatumReader<>(User.class);
        Decoder decoder = DecoderFactory.get().jsonDecoder(User.getClassSchema(),data);
        return reader.read(null, decoder);
    }

    // TODO this is untested
    public User schemaEvolutionJSON(String data,Schema from, Schema to) throws Exception {
        DatumReader<User> reader = new GenericDatumReader<>(from,to);
        Decoder decoder = DecoderFactory.get().jsonDecoder(to,data);
        return reader.read(null, decoder);
    }
}
