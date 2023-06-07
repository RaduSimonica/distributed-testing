package ro.crownstudio.config;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MainConfig {

    private static final Logger LOGGER = LogManager.getLogger(MainConfig.class);
    private static MainConfig INSTANCE;

    @Getter
    private String rabbitHost;
    @Getter
    private String rabbitUser;
    @Getter
    private String rabbitPass;
    @Getter
    private String queueRequest;
    @Getter
    private String queueReceive;

    private MainConfig() {}
    
    public static MainConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MainConfig();
            LOGGER.debug("Created a new instance for MainConfig.");
        }
        return INSTANCE;
    }

    public void loadFromCmdArgs(CmdArgs cmdArgs) {
        String rabbitUserEnv = System.getenv("RABBITMQ_DEFAULT_USER");
        String rabbitPassEnv = System.getenv("RABBITMQ_DEFAULT_PASS");

        if (rabbitUserEnv == null || rabbitUserEnv.isBlank() || rabbitPassEnv == null || rabbitPassEnv.isBlank()) {
            LOGGER.debug("Cannot find RabbitMQ user or password in environment variables. Trying to grab from cmd args.");
            rabbitUser = cmdArgs.getRabbitUser();
            rabbitPass = cmdArgs.getRabbitPass();
        } else {
            LOGGER.debug("Successfully set Rabbit user / pass from environment variables.");
            rabbitUser = rabbitUserEnv;
            rabbitPass = rabbitPassEnv;
        }

        rabbitHost = cmdArgs.getRabbitHost();
        queueRequest = cmdArgs.getRequestQueue();
        queueReceive = cmdArgs.getReceiveQueue();

        LOGGER.debug("Successfully loaded MainConfig from CmdArgs.");
    }
}
