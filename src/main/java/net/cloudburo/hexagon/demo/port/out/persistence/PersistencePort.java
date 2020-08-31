package net.cloudburo.hexagon.demo.port.out.persistence;

import net.cloudburo.hexagon.demo.domain.User;

public interface PersistencePort {

    public User createUser(User data);
    public User readUser(String id);
}
