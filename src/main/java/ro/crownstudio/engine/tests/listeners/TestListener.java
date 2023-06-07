package ro.crownstudio.engine.tests.listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;
import ro.crownstudio.enums.Status;
import ro.crownstudio.pojo.Test;

public class TestListener implements ITestListener {

    private final Test test;

    public TestListener(Test test) {
        this.test = test;
    }

    public void onTestStart(ITestResult result) {
        System.out.println("Starting test with ID: " + test.getId());
        test.setStatus(Status.RUNNING);
    }

    public void onTestSuccess(ITestResult result) {
        System.out.println("Test success " + test.getId());
        test.setStatus(Status.PASSED);
    }

    public void onTestFailure(ITestResult result) {
        System.out.println("Test failed " + test.getId());
        test.setStatus(Status.FAILED);
    }

    public void onTestSkipped(ITestResult result) {
        System.out.println("Test skipped " + test.getId());
        test.setStatus(Status.SKIPPED);
    }
}
