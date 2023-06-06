package ro.crownstudio.config;

import lombok.Getter;


public class MainConfig {
    
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
        }
        return INSTANCE;
    }

    public void loadFromCmdArgs(CmdArgs cmdArgs) {
        String rabbitUserEnv = System.getenv("RABBITMQ_DEFAULT_USER");
        String rabbitPassEnv = System.getenv("RABBITMQ_DEFAULT_PASS");

        if (rabbitUserEnv == null || rabbitUserEnv.isBlank() || rabbitPassEnv == null || rabbitPassEnv.isBlank()) {
            System.out.println(
                    "Cannot find RabbitMQ user or password in environment variables. Trying to grab from cmd args."
            );
            rabbitUser = cmdArgs.getRabbitUser();
            rabbitPass = cmdArgs.getRabbitPass();
        } else {
            rabbitUser = rabbitUserEnv;
            rabbitPass = rabbitPassEnv;
        }

        rabbitHost = cmdArgs.getRabbitHost();
        queueRequest = cmdArgs.getRequestQueue();
        queueReceive = cmdArgs.getReceiveQueue();
    }
}
