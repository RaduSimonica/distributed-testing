package ro.crownstudio.engine.tests.listeners;

import org.testng.ISuite;
import org.testng.ISuiteListener;

public class SuiteListener implements ISuiteListener {

    public void onStart(ISuite suite) {
        System.out.println("Suite %s started".formatted(suite.getName()));
    }

    public void onFinish(ISuite suite) {
        System.out.println("Suite %s finished.".formatted(suite.getName()));
    }
}
