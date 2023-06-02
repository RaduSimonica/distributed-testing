package ro.crownstudio.config;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import lombok.Getter;

@Getter
public class CmdArgs {

    @Parameter(names = {"-p", "--publisher"}, description = "Starts the app as test publisher")
    private boolean isPublisher;
    @Parameter(names = {"-w", "--worker"}, description = "Starts the app as worker")
    private boolean isWorker;
    @Parameter(names = {"--suite"}, description = "The suite name to run (must be in resources dir)")
    private String suite;

    @Parameter(names = {"--rabbitHost"}, description = "The hostname for RabbitMQ", required = true)
    private String rabbitHost;
    @Parameter(names = {"--rabbitUser"}, description = "Username for RabbitMQ")
    private String rabbitUser;
    @Parameter(names = {"--rabbitPass"}, description = "Password for RabbitMQ", password = true)
    private String rabbitPass;

    @Parameter(names = {"--requestQueue"}, description = "Request queue for sending the tests", required = true)
    private String requestQueue;
    @Parameter(names = {"--receiveQueue"}, description = "Receive queue for getting back test results")
    private String receiveQueue;

    public CmdArgs(String[] args) {
        JCommander.newBuilder().addObject(this).build().parse(args);
    }
}
