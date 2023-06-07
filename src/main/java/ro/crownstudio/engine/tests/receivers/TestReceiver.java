package ro.crownstudio.engine.tests.receivers;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.crownstudio.engine.rabbit.RabbitConnector;
import ro.crownstudio.engine.rabbit.TestConsumer;

public class TestReceiver extends Receiver {

    private final static Logger LOGGER = LogManager.getLogger(TestReceiver.class);

    @Override
    public void waitForConsume() {
        try (Connection connection = RabbitConnector.getInstance().getFactory().newConnection()) {
            Channel channel = connection.createChannel();
            channel.queueDeclare(CONFIG.getQueueRequest(), false, false, false, null);
            channel.basicQos(PREFETCH_COUNT);
            Consumer consumer = new TestConsumer(channel);
            channel.basicConsume(CONFIG.getQueueRequest(), false, consumer);

            LOGGER.info("Waiting for tests to run...");

            while (true) {
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            LOGGER.error("Something went wrong while waiting for tests to run", e);
        }
    }
}
