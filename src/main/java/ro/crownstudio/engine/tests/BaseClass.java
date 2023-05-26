package ro.crownstudio.engine.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class BaseClass {

    protected final static int WAIT_IN_MS = 5000;

    @BeforeSuite
    public void setupSuite() {
        System.out.println("I am in before suite.");
    }

    @BeforeClass
    public void setupClass() {
        System.out.println("I am in before class.");
    }

    @BeforeMethod
    public void setupMethod() {
        System.out.println("I am in before method.");
    }
}
