/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package net.cloudburo.hexagon.demo.domain;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@SuppressWarnings("all")
/** The header record stores important (meta-) information about the record, certain fields are optional in case an initial creation is done */
@org.apache.avro.specific.AvroGenerated
public class Header extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -9035598492821621757L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Header\",\"namespace\":\"net.cloudburo.hexagon.demo.domain\",\"doc\":\"The header record stores important (meta-) information about the record, certain fields are optional in case an initial creation is done\",\"fields\":[{\"name\":\"avroFingerprint\",\"type\":\"long\",\"doc\":\"Mandatory: The fingerprint of the schema used by record producer, http://avro.apache.org/docs/1.8.2/spec.html#schema_fingerprints.\"},{\"name\":\"lastUpdateLoginId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"doc\":\"Mandatory: Login id  who produced the record\"},{\"name\":\"lastUpdateTimestamp\",\"type\":[\"null\",\"long\"],\"doc\":\"Optional: The Update timestamp when the record was persisted. Null for the update of a record, will be newly calculated by persistency adpapter\",\"default\":null,\"logical-type\":\"time-micros\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<Header> ENCODER =
      new BinaryMessageEncoder<Header>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<Header> DECODER =
      new BinaryMessageDecoder<Header>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<Header> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<Header> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<Header>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this Header to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a Header from a ByteBuffer. */
  public static Header fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  /** Mandatory: The fingerprint of the schema used by record producer, http://avro.apache.org/docs/1.8.2/spec.html#schema_fingerprints. */
   private long avroFingerprint;
  /** Mandatory: Login id  who produced the record */
   private java.lang.String lastUpdateLoginId;
  /** Optional: The Update timestamp when the record was persisted. Null for the update of a record, will be newly calculated by persistency adpapter */
   private java.lang.Long lastUpdateTimestamp;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public Header() {}

  /**
   * All-args constructor.
   * @param avroFingerprint Mandatory: The fingerprint of the schema used by record producer, http://avro.apache.org/docs/1.8.2/spec.html#schema_fingerprints.
   * @param lastUpdateLoginId Mandatory: Login id  who produced the record
   * @param lastUpdateTimestamp Optional: The Update timestamp when the record was persisted. Null for the update of a record, will be newly calculated by persistency adpapter
   */
  public Header(java.lang.Long avroFingerprint, java.lang.String lastUpdateLoginId, java.lang.Long lastUpdateTimestamp) {
    this.avroFingerprint = avroFingerprint;
    this.lastUpdateLoginId = lastUpdateLoginId;
    this.lastUpdateTimestamp = lastUpdateTimestamp;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return avroFingerprint;
    case 1: return lastUpdateLoginId;
    case 2: return lastUpdateTimestamp;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: avroFingerprint = (java.lang.Long)value$; break;
    case 1: lastUpdateLoginId = (java.lang.String)value$; break;
    case 2: lastUpdateTimestamp = (java.lang.Long)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'avroFingerprint' field.
   * @return Mandatory: The fingerprint of the schema used by record producer, http://avro.apache.org/docs/1.8.2/spec.html#schema_fingerprints.
   */
  public java.lang.Long getAvroFingerprint() {
    return avroFingerprint;
  }


  /**
   * Gets the value of the 'lastUpdateLoginId' field.
   * @return Mandatory: Login id  who produced the record
   */
  public java.lang.String getLastUpdateLoginId() {
    return lastUpdateLoginId;
  }


  /**
   * Gets the value of the 'lastUpdateTimestamp' field.
   * @return Optional: The Update timestamp when the record was persisted. Null for the update of a record, will be newly calculated by persistency adpapter
   */
  public java.lang.Long getLastUpdateTimestamp() {
    return lastUpdateTimestamp;
  }


  /**
   * Creates a new Header RecordBuilder.
   * @return A new Header RecordBuilder
   */
  public static net.cloudburo.hexagon.demo.domain.Header.Builder newBuilder() {
    return new net.cloudburo.hexagon.demo.domain.Header.Builder();
  }

  /**
   * Creates a new Header RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new Header RecordBuilder
   */
  public static net.cloudburo.hexagon.demo.domain.Header.Builder newBuilder(net.cloudburo.hexagon.demo.domain.Header.Builder other) {
    return new net.cloudburo.hexagon.demo.domain.Header.Builder(other);
  }

  /**
   * Creates a new Header RecordBuilder by copying an existing Header instance.
   * @param other The existing instance to copy.
   * @return A new Header RecordBuilder
   */
  public static net.cloudburo.hexagon.demo.domain.Header.Builder newBuilder(net.cloudburo.hexagon.demo.domain.Header other) {
    return new net.cloudburo.hexagon.demo.domain.Header.Builder(other);
  }

  /**
   * RecordBuilder for Header instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Header>
    implements org.apache.avro.data.RecordBuilder<Header> {

    /** Mandatory: The fingerprint of the schema used by record producer, http://avro.apache.org/docs/1.8.2/spec.html#schema_fingerprints. */
    private long avroFingerprint;
    /** Mandatory: Login id  who produced the record */
    private java.lang.String lastUpdateLoginId;
    /** Optional: The Update timestamp when the record was persisted. Null for the update of a record, will be newly calculated by persistency adpapter */
    private java.lang.Long lastUpdateTimestamp;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(net.cloudburo.hexagon.demo.domain.Header.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.avroFingerprint)) {
        this.avroFingerprint = data().deepCopy(fields()[0].schema(), other.avroFingerprint);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.lastUpdateLoginId)) {
        this.lastUpdateLoginId = data().deepCopy(fields()[1].schema(), other.lastUpdateLoginId);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.lastUpdateTimestamp)) {
        this.lastUpdateTimestamp = data().deepCopy(fields()[2].schema(), other.lastUpdateTimestamp);
        fieldSetFlags()[2] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing Header instance
     * @param other The existing instance to copy.
     */
    private Builder(net.cloudburo.hexagon.demo.domain.Header other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.avroFingerprint)) {
        this.avroFingerprint = data().deepCopy(fields()[0].schema(), other.avroFingerprint);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.lastUpdateLoginId)) {
        this.lastUpdateLoginId = data().deepCopy(fields()[1].schema(), other.lastUpdateLoginId);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.lastUpdateTimestamp)) {
        this.lastUpdateTimestamp = data().deepCopy(fields()[2].schema(), other.lastUpdateTimestamp);
        fieldSetFlags()[2] = true;
      }
    }

    /**
      * Gets the value of the 'avroFingerprint' field.
      * Mandatory: The fingerprint of the schema used by record producer, http://avro.apache.org/docs/1.8.2/spec.html#schema_fingerprints.
      * @return The value.
      */
    public java.lang.Long getAvroFingerprint() {
      return avroFingerprint;
    }

    /**
      * Sets the value of the 'avroFingerprint' field.
      * Mandatory: The fingerprint of the schema used by record producer, http://avro.apache.org/docs/1.8.2/spec.html#schema_fingerprints.
      * @param value The value of 'avroFingerprint'.
      * @return This builder.
      */
    public net.cloudburo.hexagon.demo.domain.Header.Builder setAvroFingerprint(long value) {
      validate(fields()[0], value);
      this.avroFingerprint = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'avroFingerprint' field has been set.
      * Mandatory: The fingerprint of the schema used by record producer, http://avro.apache.org/docs/1.8.2/spec.html#schema_fingerprints.
      * @return True if the 'avroFingerprint' field has been set, false otherwise.
      */
    public boolean hasAvroFingerprint() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'avroFingerprint' field.
      * Mandatory: The fingerprint of the schema used by record producer, http://avro.apache.org/docs/1.8.2/spec.html#schema_fingerprints.
      * @return This builder.
      */
    public net.cloudburo.hexagon.demo.domain.Header.Builder clearAvroFingerprint() {
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'lastUpdateLoginId' field.
      * Mandatory: Login id  who produced the record
      * @return The value.
      */
    public java.lang.String getLastUpdateLoginId() {
      return lastUpdateLoginId;
    }

    /**
      * Sets the value of the 'lastUpdateLoginId' field.
      * Mandatory: Login id  who produced the record
      * @param value The value of 'lastUpdateLoginId'.
      * @return This builder.
      */
    public net.cloudburo.hexagon.demo.domain.Header.Builder setLastUpdateLoginId(java.lang.String value) {
      validate(fields()[1], value);
      this.lastUpdateLoginId = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'lastUpdateLoginId' field has been set.
      * Mandatory: Login id  who produced the record
      * @return True if the 'lastUpdateLoginId' field has been set, false otherwise.
      */
    public boolean hasLastUpdateLoginId() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'lastUpdateLoginId' field.
      * Mandatory: Login id  who produced the record
      * @return This builder.
      */
    public net.cloudburo.hexagon.demo.domain.Header.Builder clearLastUpdateLoginId() {
      lastUpdateLoginId = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'lastUpdateTimestamp' field.
      * Optional: The Update timestamp when the record was persisted. Null for the update of a record, will be newly calculated by persistency adpapter
      * @return The value.
      */
    public java.lang.Long getLastUpdateTimestamp() {
      return lastUpdateTimestamp;
    }

    /**
      * Sets the value of the 'lastUpdateTimestamp' field.
      * Optional: The Update timestamp when the record was persisted. Null for the update of a record, will be newly calculated by persistency adpapter
      * @param value The value of 'lastUpdateTimestamp'.
      * @return This builder.
      */
    public net.cloudburo.hexagon.demo.domain.Header.Builder setLastUpdateTimestamp(java.lang.Long value) {
      validate(fields()[2], value);
      this.lastUpdateTimestamp = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'lastUpdateTimestamp' field has been set.
      * Optional: The Update timestamp when the record was persisted. Null for the update of a record, will be newly calculated by persistency adpapter
      * @return True if the 'lastUpdateTimestamp' field has been set, false otherwise.
      */
    public boolean hasLastUpdateTimestamp() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'lastUpdateTimestamp' field.
      * Optional: The Update timestamp when the record was persisted. Null for the update of a record, will be newly calculated by persistency adpapter
      * @return This builder.
      */
    public net.cloudburo.hexagon.demo.domain.Header.Builder clearLastUpdateTimestamp() {
      lastUpdateTimestamp = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Header build() {
      try {
        Header record = new Header();
        record.avroFingerprint = fieldSetFlags()[0] ? this.avroFingerprint : (java.lang.Long) defaultValue(fields()[0]);
        record.lastUpdateLoginId = fieldSetFlags()[1] ? this.lastUpdateLoginId : (java.lang.String) defaultValue(fields()[1]);
        record.lastUpdateTimestamp = fieldSetFlags()[2] ? this.lastUpdateTimestamp : (java.lang.Long) defaultValue(fields()[2]);
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<Header>
    WRITER$ = (org.apache.avro.io.DatumWriter<Header>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<Header>
    READER$ = (org.apache.avro.io.DatumReader<Header>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
