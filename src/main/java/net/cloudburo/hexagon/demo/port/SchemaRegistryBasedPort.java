package net.cloudburo.hexagon.demo.port;

import net.cloudburo.hexagon.demo.schemaregistry.SchemaRegistry;

public class SchemaRegistryBasedPort {

    protected SchemaRegistry registry;

    public void setRegistry(SchemaRegistry registry) {
        this.registry = registry;
    }
}
