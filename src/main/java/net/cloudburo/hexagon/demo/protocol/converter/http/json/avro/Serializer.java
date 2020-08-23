package net.cloudburo.hexagon.demo.protocol.converter.http.json.avro;

/**
 * Provides low level interface for serialization from object of a generic type into the byte array.
 */
public interface Serializer<T> {

    /**
     * Object serialization
     *
     * @param data - source of the data
     * @return byte[] - serialized object
     * @throws DataSerializationException - signals that some exception of some sort has occurred
     *                                    during the data serialization
     */
    byte[] serialize(T data) throws DataSerializationException;
}
