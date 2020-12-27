package net.cloudburo.hexagon.demo.port.out.user.persistence;

import net.cloudburo.hexagon.demo.domain.User;
import net.cloudburo.hexagon.demo.port.SchemaRegistryBasedPort;
import org.apache.avro.Schema;
import org.apache.avro.SchemaNormalization;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.ByteArrayOutputStream;

public abstract class UserPersistencePort extends SchemaRegistryBasedPort {

    protected long fingerprint = SchemaNormalization.parsingFingerprint64(User.getClassSchema());

    // Domain Object Maintain Operations
    public abstract  User createUser(User data) throws Exception;
    public abstract User readUser(String id) throws Exception;


    protected String serializeJSON(User request) throws Exception {
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

    protected User deSerializeJSON(String data) throws Exception {
        DatumReader<User> reader = new SpecificDatumReader<>(User.class);
        Decoder decoder = DecoderFactory.get().jsonDecoder(User.getClassSchema(),data);
        return reader.read(null, decoder);
    }

    // TODO this is untested
    protected User schemaEvolutionJSON(String data,Schema from, Schema to) throws Exception {
        DatumReader<User> reader = new GenericDatumReader<>(from,to);
        Decoder decoder = DecoderFactory.get().jsonDecoder(to,data);
        return reader.read(null, decoder);
    }
}
