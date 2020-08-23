package net.cloudburo.hexagon.demo.port.in.maintenance;


import com.cloudburo.hexagon.demo.domain.EmailData;

public interface MaintenancePort {

    public EmailData readUser(String id) throws Exception;
    public EmailData createUser(EmailData emailData) throws Exception;
}
