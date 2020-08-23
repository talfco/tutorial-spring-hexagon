package net.cloudburo.hexagon.demo.protocol.converter.http.json.avro;

import java.io.IOException;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;

import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;

import static org.springframework.http.converter.StringHttpMessageConverter.DEFAULT_CHARSET;

/**
 * This converter supports application/avro-json format with {@code DEFAULT_CHARSET} character set.
 * @see AbstractAvroHttpMessageConverter
 * @see SpecificRecordBase
 */
public class AvroJsonHttpMessageConverter<T extends SpecificRecordBase> extends AbstractHttpMessageConverter<T> {

    private static final String APPLICATION = "application";
    private static final String AVRO_JSON_SUBTYPE = "avro-json";

    static final MediaType AVRO_JSON_MEDIA_TYPE = new MediaType(APPLICATION, AVRO_JSON_SUBTYPE, DEFAULT_CHARSET);
    public static final String AVRO_JSON = APPLICATION + "/" + AVRO_JSON_SUBTYPE;

    private final AvroSerializer<SpecificRecordBase> avroSerializer;
    private final AvroDeserializer<SpecificRecordBase> avroDeserializer;

    public AvroJsonHttpMessageConverter() {
        super(AVRO_JSON_MEDIA_TYPE);

        this.avroSerializer = new AvroSerializer<>();
        this.avroDeserializer = new AvroDeserializer<>();
    }

    /**
     * Reading accepted message and providing deserialization
     *
     * @param aClass       - target Avro object type
     * @param inputMessage - accepted message
     * @return {@code T} - converted Avro object
     * @throws IOException - on read/write issues
     */
    @SuppressWarnings("unchecked")
    @Override
    protected T readInternal(final Class<? extends T> aClass, HttpInputMessage inputMessage) throws IOException {
        T result = null;
        final byte[] data = IOUtils.toByteArray(inputMessage.getBody());
        if (data != null && data.length > 0) {
            try {
                result = (T) avroDeserializer.deserialize(data, aClass);
            } catch (DataSerializationException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return result;
    }

    /**
     * Serializing and writing Avro object into outgoing message
     *
     * @param t                 - Avro object
     * @param httpOutputMessage - outgoing message
     * @throws IOException - on read/write issues
     */
    @Override
    protected void writeInternal(final T t, final HttpOutputMessage httpOutputMessage) throws IOException {
        try {
            final byte[] data = avroSerializer.serialize(t);
            httpOutputMessage.getBody().write(data);
        } catch (DataSerializationException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Indicates whether the given converter is suitable for a class type
     *
     * @param aClass - class type
     * @return {@code boolean} value indicating whether objects of the type {@code SpecificRecordBase}
     * can be assigned to objects of {@code aClass}
     */
    @Override
    protected boolean supports(final Class<?> aClass) {
        return SpecificRecordBase.class.isAssignableFrom(aClass);
    }
}

