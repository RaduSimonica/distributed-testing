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
        rabbitHost = cmdArgs.getRabbitHost();
        rabbitUser = cmdArgs.getRabbitUser();
        rabbitPass = cmdArgs.getRabbitPass();

        queueRequest = cmdArgs.getRequestQueue();
        queueReceive = cmdArgs.getReceiveQueue();
    }
}
