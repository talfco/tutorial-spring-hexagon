package net.cloudburo.hexagon.demo.port.out.covid.persistence.adapter.elasticsearch;

import com.google.gson.JsonObject;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.IndicesExists;
import io.searchbox.indices.mapping.PutMapping;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ESPersistencyManager {

    private static Logger logger = Logger.getLogger(ESPersistencyManager.class);

    @Autowired
    private ESPersistencyConfig esPersistencyConfig;

    private JestClient esClient;


    protected JestClient getEsClient() {
        if (esClient == null)
            esClient = connectES(esPersistencyConfig.getEsurl(), esPersistencyConfig.getUsername(), esPersistencyConfig.getPassword());
        return esClient;
    }

    public void createIndex(String index) throws IOException {
        logger.info("Creating elasticsearch index: "+index);
        JestResult res = getEsClient().execute(new CreateIndex.Builder(index).build());
        if (!res.isSucceeded()) {
            logger.error(res.getErrorMessage());
            throw new IOException(res.getErrorMessage());
        }
    }

    public boolean existsIndex(String index) throws IOException {
        return getEsClient().execute(new IndicesExists.Builder(index).build()).isSucceeded();
    }

    public void updateESMapping(String index, String type, String mappingJson) throws IOException {
        JestResult res = getEsClient().execute(new PutMapping.Builder(index,type, mappingJson).build());
        if (!res.isSucceeded()) {
            logger.error(res.getErrorMessage());
            throw new IOException(res.getErrorMessage());
        }
    }

    public void createUpdateDocument(String index, String type, String docJson, String id) throws IOException {
        Index esIndex;
        if (id == null) {
            esIndex = new Index.Builder(docJson).index(index).type(type).build();
        } else {
            esIndex = new Index.Builder(docJson).index(index).type(type).id(id).build();
        }
        JestResult jestResult =  getEsClient().execute(esIndex);
        if(jestResult.isSucceeded()) {
            logger.info("Document persisted:" +index+"/"+type+"/"+id);
        }
        else {
            logger.error(jestResult);
        }
    }

    public JsonObject readDocumentByIdAsObject(String index, String id) throws IOException{
        return getEsClient().execute(new Get.Builder(index, id).build()).getJsonObject();
    }

    private  JestClient connectES(String esURL, String user, String password) {
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(
                new HttpClientConfig.Builder(esURL)
                        .multiThreaded(true)
                        .defaultMaxTotalConnectionPerRoute(2)
                        .maxTotalConnection(20)
                        .defaultCredentials(user,password)
                        .build());
        return factory.getObject();
    }

}