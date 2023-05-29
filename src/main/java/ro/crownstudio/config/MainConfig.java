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

    public boolean loadFromCmdArgs(CmdArgs cmdArgs) {
        // TODO: Add error loggers here.
        if (cmdArgs.getRabbitHost() == null || cmdArgs.getRabbitUser().isBlank()) {
            return false;
        }
        if (cmdArgs.getRabbitUser() == null || cmdArgs.getRabbitUser().isBlank()) {
            return false;
        }
        if (cmdArgs.getRabbitPass() == null || cmdArgs.getRabbitPass().isBlank()) {
            return false;
        }
        if (cmdArgs.getRequestQueue() == null || cmdArgs.getRequestQueue().isBlank()) {
            return false;
        }
        if (cmdArgs.getReceiveQueue() == null || cmdArgs.getReceiveQueue().isBlank()) {
            return false;
        }

        rabbitHost = cmdArgs.getRabbitHost();
        rabbitUser = cmdArgs.getRabbitUser();
        rabbitPass = cmdArgs.getRabbitPass();

        queueRequest = cmdArgs.getRequestQueue();
        queueReceive = cmdArgs.getReceiveQueue();

        return true;
    }
}
