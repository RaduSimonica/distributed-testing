package ro.crownstudio;


import ro.crownstudio.config.CmdArgs;
import ro.crownstudio.config.MainConfig;
import ro.crownstudio.engine.tests.TestDistributor;
import ro.crownstudio.engine.tests.TestReceiver;

public class Main {

    public static void main(String[] args) {
        CmdArgs cmdArgs = new CmdArgs(args);
        boolean configLoaded = MainConfig.getInstance().loadFromCmdArgs(cmdArgs);

        if (!configLoaded) {
            throw new RuntimeException(
                    "Failed to load config from command line arguments. " +
                            "Please make sure to provide all mandatory configs"
            );
        }

        if (cmdArgs.isPublisher()) {
            if (cmdArgs.getSuite() == null || cmdArgs.getSuite().isEmpty()) {
                throw new RuntimeException("Please provide a suite.");
            }

            TestDistributor distributor = new TestDistributor();

            if (!distributor.distributeTests(cmdArgs.getSuite())) {
                System.exit(1);
            }

        } else if (cmdArgs.isWorker()) {
            TestReceiver.waitForTests();
        }
    }
}