package net.cloudburo.hexagon.demo.port.in.maintenance;


import net.cloudburo.hexagon.demo.domain.User;
import net.cloudburo.hexagon.demo.schemaregistry.SchemaRegistry;
import org.apache.avro.Schema;

public interface MaintenancePort {

    public User readUser(String id) throws Exception;
    public User createUser(User emailData) throws Exception;
    public SchemaRegistry getSchemaRegistry() throws Exception;
}
