package net.cloudburo.hexagon.demo.port.out.persistence.adapter.sandbox;

import com.cloudburo.hexagon.demo.domain.EmailData;
import net.cloudburo.hexagon.demo.port.out.persistence.PersistencePort;

import java.util.HashMap;

public class SandboxPersistency implements PersistencePort {

    private static HashMap<String, EmailData> cache = new HashMap<>();

    @Override
    public EmailData createUser(EmailData data) {
        // Ok here we would store the Avro JSON Document
        data.put("uid", "1234567890");
        cache.put(data.getUid(), data);
        return data;
    }

    @Override
    public EmailData readUser(String id) {
        return cache.get(id);
    }
}
