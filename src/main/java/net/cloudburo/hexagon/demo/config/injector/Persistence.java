package net.cloudburo.hexagon.demo.config.injector;

public class Persistence {
    PersistenceElasticSearch esPersistence;

    public PersistenceElasticSearch getEsPersistence() {
        return esPersistence;
    }

    public void setEsPersistence(PersistenceElasticSearch esPersistence) {
        this.esPersistence = esPersistence;
    }
}
