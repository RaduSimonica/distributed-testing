package ro.crownstudio.engine.tests.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ISuite;
import org.testng.ISuiteListener;

public class SuiteListener implements ISuiteListener {

    private final static Logger LOGGER = LogManager.getLogger(SuiteListener.class);

    public void onStart(ISuite suite) {
        LOGGER.info("Suite %s started".formatted(suite.getName()));
    }

    public void onFinish(ISuite suite) {
        LOGGER.info("Suite %s finished.".formatted(suite.getName()));
    }
}
