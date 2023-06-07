package ro.crownstudio.engine.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class BaseClass {

    protected final static int WAIT_IN_MS = 5000;

    @BeforeSuite
    public void setupSuite() {
        // Not yet implemented
    }

    @BeforeClass
    public void setupClass() {
        // Not yet implemented
    }

    @BeforeMethod
    public void setupMethod() {
        // Not yet implemented
    }
}
