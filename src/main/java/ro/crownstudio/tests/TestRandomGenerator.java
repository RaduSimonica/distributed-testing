package ro.crownstudio.tests;

import org.testng.annotations.Test;

import java.util.Random;

import static org.testng.AssertJUnit.assertTrue;

public class TestRandomGenerator {

    private final int MIN_TIME_SECONDS = 1;
    private final int MAX_TIME_SECONDS = 10;

    @Test
    public void testRandom() throws InterruptedException {
        System.out.println("This will wait at random. This test may also fail or pass at random.");

        Random random = new Random();
        int waitTime = random.nextInt(MIN_TIME_SECONDS, MAX_TIME_SECONDS);
        System.out.println("Waiting for %s seconds...".formatted(waitTime));
        Thread.sleep(waitTime * 1000L);

        assertTrue("I failed! It's intended :).", random.nextBoolean());
    }
}
