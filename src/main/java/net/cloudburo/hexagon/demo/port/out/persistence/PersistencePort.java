package net.cloudburo.hexagon.demo.port.out.persistence;

import com.cloudburo.hexagon.demo.domain.EmailData;

public interface PersistencePort {

    public EmailData createUser(EmailData data);
    public EmailData readUser(String id);
}
