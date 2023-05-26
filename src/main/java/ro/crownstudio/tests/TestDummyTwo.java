package ro.crownstudio.tests;

import org.testng.annotations.Test;
import ro.crownstudio.engine.tests.BaseClass;

import java.util.Random;

import static org.testng.AssertJUnit.assertTrue;

public class TestDummyTwo extends BaseClass {

    @Test
    public void test2() throws InterruptedException {
        System.out.println("I am the 2nd test.");
        System.out.println("Waiting...");
        Thread.sleep(WAIT_IN_MS);
        assertTrue("I failed!", new Random().nextBoolean());
    }
}
