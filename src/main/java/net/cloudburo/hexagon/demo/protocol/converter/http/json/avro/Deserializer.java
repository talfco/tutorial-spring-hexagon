package net.cloudburo.hexagon.demo.protocol.converter.http.json.avro;

/**
 * Provides low level interface for deserialization from the byte array
 */
public interface Deserializer<T> {

    /**
     * Object deserialization
     *
     * @param data   - source of the data
     * @param aClass - target class
     * @return T generic object
     * @throws DataSerializationException - signals that some exception of some sort has occurred
     *                                    during the data deserialization
     */
    T deserialize(byte[] data, Class<? extends T> aClass) throws DataSerializationException;

}