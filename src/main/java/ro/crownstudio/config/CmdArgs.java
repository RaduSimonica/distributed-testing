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
    @Parameter(names = {"-s", "--suite"}, description = "The suite name to run (must be in resources dir)")
    private String suite;

    @Parameter(names = {"-h", "--rabbitHost"}, description = "The hostname for RabbitMQ")
    private String rabbitHost;
    @Parameter(names = {"-u", "--rabbitUser"}, description = "Username for RabbitMQ")
    private String rabbitUser;
    @Parameter(names = {"-p", "--rabbitPass"}, description = "Password for RabbitMQ", password = true)
    private String rabbitPass;

    @Parameter(names = {"-t", "--requestQueue"}, description = "Request queue for sending the tests")
    private String requestQueue;
    @Parameter(names = {"-r", "--receiveQueue"}, description = "Receive queue for getting back test results")
    private String receiveQueue;

    public CmdArgs(String[] args) {
        JCommander.newBuilder().addObject(this).build().parse(args);
    }
}
