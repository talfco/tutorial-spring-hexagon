package net.cloudburo.hexagon.demo.port.in.user.maintain;

import net.cloudburo.hexagon.demo.domain.User;
import net.cloudburo.hexagon.demo.schemaregistry.SchemaRegistry;


public interface UserMaintainPort {

    public User readUser(String id) throws Exception;
    public User createUser(User emailData) throws Exception;
    public SchemaRegistry getSchemaRegistry() throws Exception;
}
