package ro.crownstudio.engine.tests.listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    private String id;

    public TestListener(String id) {
        this.id = id;
    }

    public void onTestStart(ITestResult result) {
        System.out.println("Starting test with ID: " + id);
        // not implemented
    }

    public void onTestSuccess(ITestResult result) {
        System.out.println("Test success " + id);
        // not implemented
    }

    public void onTestFailure(ITestResult result) {
        System.out.println("Test fail " + id);
        // not implemented
    }

    public void onTestSkipped(ITestResult result) {
        // not implemented
    }
}
