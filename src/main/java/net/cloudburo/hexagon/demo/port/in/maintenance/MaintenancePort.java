package net.cloudburo.hexagon.demo.port.in.maintenance;


import net.cloudburo.hexagon.demo.domain.User;

public interface MaintenancePort {

    public User readUser(String id) throws Exception;
    public User createUser(User emailData) throws Exception;
}
