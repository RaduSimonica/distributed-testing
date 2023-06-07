package ro.crownstudio.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import ro.crownstudio.engine.tests.BaseClass;

import java.util.Random;

import static org.testng.AssertJUnit.assertTrue;

public class TestDummyFour extends BaseClass {

    private final static Logger LOGGER = LogManager.getLogger(TestDummyFour.class);

    @Test
    public void test4() throws InterruptedException {
        LOGGER.info("I am the 4th test.");
        LOGGER.info("Waiting in test for %sms".formatted(WAIT_IN_MS));
        Thread.sleep(WAIT_IN_MS);
        assertTrue("I failed!", new Random().nextBoolean());
    }
}
