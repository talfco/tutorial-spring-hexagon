package net.cloudburo.hexagon.demo.port.out.covid.persistence;

import net.cloudburo.hexagon.demo.domain.covid.CovidCase;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.ByteArrayOutputStream;

public class CovidSerializer {

    public static String serializeJSON(CovidCase request) throws Exception {
        DatumWriter<CovidCase> writer = new SpecificDatumWriter<CovidCase>(CovidCase.class);
        byte[] data = new byte[0];
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Encoder jsonEncoder = null;
        jsonEncoder = EncoderFactory.get().jsonEncoder(CovidCase.getClassSchema(), stream);
        writer.write(request, jsonEncoder);
        jsonEncoder.flush();
        data = stream.toByteArray();
        return new String(data);
    }

    public static CovidCase deSerializeJSON(String data) throws Exception {
        DatumReader<CovidCase> reader = new SpecificDatumReader<>(CovidCase.class);
        Decoder decoder = DecoderFactory.get().jsonDecoder(CovidCase.getClassSchema(),data);
        return reader.read(null, decoder);
    }

    // TODO this is untested
    public CovidCase schemaEvolutionJSON(String data, Schema from, Schema to) throws Exception {
        DatumReader<CovidCase> reader = new GenericDatumReader<>(from,to);
        Decoder decoder = DecoderFactory.get().jsonDecoder(to,data);
        return reader.read(null, decoder);
    }
}
