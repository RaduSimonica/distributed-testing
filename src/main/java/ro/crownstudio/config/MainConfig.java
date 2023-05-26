package ro.crownstudio.config;

import lombok.Getter;

import java.io.IOException;
import java.util.Properties;

public class MainConfig {
    
    private static MainConfig INSTANCE;
    private final Properties properties;

    @Getter
    private String rabbitHost;
    @Getter
    private String rabbitUser;
    @Getter
    private String rabbitPass;
    @Getter
    private String queueRequest;

    private MainConfig() {
        properties = new Properties();
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream("app.properties.local"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load main properties file", e);
        }

        if (!loadProperties()) {
            throw new RuntimeException("Failed to get properties from main properties object");
        }
    }
    
    public static MainConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MainConfig();
        }
        return INSTANCE;
    }

    private boolean loadProperties() {
        try {
            rabbitHost = properties.getProperty("rabbit.host");
            rabbitUser = properties.getProperty("rabbit.user");
            rabbitPass = properties.getProperty("rabbit.pass");

            queueRequest = properties.getProperty("queue.request");
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
