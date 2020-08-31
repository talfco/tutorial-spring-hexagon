package net.cloudburo.hexagon.demo.port.out.persistence.adapter.sandbox;

import net.cloudburo.hexagon.demo.domain.Header;
import net.cloudburo.hexagon.demo.domain.Ids;
import net.cloudburo.hexagon.demo.domain.User;
import net.cloudburo.hexagon.demo.port.out.persistence.PersistencePort;
import org.apache.avro.SchemaNormalization;

import org.apache.log4j.Logger;

import java.util.HashMap;

public class SandboxPersistency implements PersistencePort {

    private static Logger logger = Logger.getLogger(SandboxPersistency.class);

    private static HashMap<String, User> cache = new HashMap<>();

    @Override
    public User createUser(User data) {
        // Ok here we would store the Avro JSON Document
        Ids.Builder builder;
        // Ids section may empty
        if (data.getIds()==null) {
            builder = Ids.newBuilder();
        } else {
            builder = Ids.newBuilder(data.getIds());
        }
        Ids ids = builder
                .setUid("12345678")
                .setDomain("8080")
                .build();
        long fingerprint = SchemaNormalization.parsingFingerprint64(User.getClassSchema());
        Header header = Header.newBuilder(data.getHeader())
                .setAvroFingerprint(fingerprint)
                .setLastUpdateTimestamp(java.lang.System.currentTimeMillis())
                .build();
        User user = User.newBuilder(data)
                .setIds(ids)
                .setHeader(header)
                .build();
        logger.info("Persisting Avro-JSON "+user.toString());
        cache.put(user.getIds().getUid(), user);
        return user;
    }

    @Override
    public User readUser(String id) {
        return cache.get(id);
    }


}
