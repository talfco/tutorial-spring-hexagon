package net.cloudburo.hexagon.demo.config.injector;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

//@Configuration
//@PropertySource("classpath:configprops.properties")
//@ConfigurationProperties(prefix = "injector")
public class InjectorConfig {

    @Validated
    public static class EsCredentials {
        @NotNull
        private String esURL;
        private String user;
        private String password;

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEsURL() {
            return esURL;
        }

        public void setEsURL(String esURL) {
            this.esURL = esURL;
        }
    }

    private EsCredentials esCredentials;

    public EsCredentials getEsCredentials() {
        return esCredentials;
    }

    public void setEsCredentials(EsCredentials esCredentials) {
        this.esCredentials = esCredentials;
    }
}
