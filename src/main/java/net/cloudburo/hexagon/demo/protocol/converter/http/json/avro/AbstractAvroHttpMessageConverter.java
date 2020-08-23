package net.cloudburo.hexagon.demo.protocol.converter.http.json.avro;

import static org.springframework.http.converter.StringHttpMessageConverter.DEFAULT_CHARSET;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;

public abstract class AbstractAvroHttpMessageConverter<T extends SpecificRecordBase>
        extends AbstractHttpMessageConverter<T> {

    private static final String APPLICATION = "application";
    private static final String AVRO_JSON_SUBTYPE = "avro-json";

    static final MediaType AVRO_JSON_MEDIA_TYPE = new MediaType(APPLICATION, AVRO_JSON_SUBTYPE, DEFAULT_CHARSET);

    public static final String AVRO_JSON = APPLICATION + "/" + AVRO_JSON_SUBTYPE;

    AbstractAvroHttpMessageConverter(final MediaType supportedMediaType) {
        super(supportedMediaType);
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

