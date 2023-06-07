package ro.crownstudio.engine.tests.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;
import ro.crownstudio.enums.Status;
import ro.crownstudio.pojo.Test;

public class TestListener implements ITestListener {

    private final static Logger LOGGER = LogManager.getLogger(TestListener.class);

    private final Test test;

    public TestListener(Test test) {
        this.test = test;
    }

    public void onTestStart(ITestResult result) {
        LOGGER.info("Starting test with ID: " + test.getId());
        test.setStatus(Status.RUNNING);
    }

    public void onTestSuccess(ITestResult result) {
        LOGGER.info("Test success " + test.getId());
        test.setStatus(Status.PASSED);
    }

    public void onTestFailure(ITestResult result) {
        LOGGER.info("Test failed " + test.getId());
        test.setStatus(Status.FAILED);
    }

    public void onTestSkipped(ITestResult result) {
        LOGGER.info("Test skipped " + test.getId());
        test.setStatus(Status.SKIPPED);
    }
}
