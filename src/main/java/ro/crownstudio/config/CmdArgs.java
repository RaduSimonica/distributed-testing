package ro.crownstudio.config;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import lombok.Getter;

public class CmdArgs {

    @Getter
    @Parameter(names = {"-p", "--publisher"}, description = "Starts the app as test publisher")
    private boolean isPublisher = false;

    @Getter
    @Parameter(names = {"-w", "--worker"}, description = "Starts the app as worker")
    private boolean isWorker = false;

    @Getter
    @Parameter(names = {"-s", "--suite"}, description = "The suite name to run (must be in resources dir)")
    private String suite;

    public CmdArgs(String[] args) {
        JCommander.newBuilder().addObject(this).build().parse(args);
    }
}
