package ro.crownstudio;


import ro.crownstudio.engine.tests.TestDistributor;
import ro.crownstudio.engine.tests.TestReceiver;

public class Main {

    public static void main(String[] args) {
        if (args != null && args.length > 0 && args[0].equals("publisher")) {
            TestDistributor distributor = new TestDistributor();

            if (!distributor.distributeTests(args[1])) {
                System.exit(1);
            }
        } else {
            TestReceiver.waitForTests();
        }
    }
}