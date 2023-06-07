package ro.crownstudio.engine.tests.receivers;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.crownstudio.engine.rabbit.RabbitConnector;
import ro.crownstudio.engine.rabbit.ResultsConsumer;
import ro.crownstudio.pojo.Test;

import java.util.List;

public class ResultsReceiver extends Receiver {

    private final static Logger LOGGER = LogManager.getLogger(ResultsReceiver.class);
    private final List<Test> tests;

    public ResultsReceiver(List<Test> tests) {
        this.tests = tests;
    }

    @Override
    public void waitForConsume() {
        try (Connection connection = RabbitConnector.getInstance().getFactory().newConnection()) {
            Channel channel = connection.createChannel();
            channel.queueDeclare(CONFIG.getQueueReceive(), false, false, false, null);
            channel.basicQos(PREFETCH_COUNT);

            Consumer consumer = new ResultsConsumer(channel, tests);
            channel.basicConsume(CONFIG.getQueueReceive(), false, consumer);

            LOGGER.info("Waiting for %s results...".formatted(tests.size()));

            while (!tests.stream().allMatch(x -> x.getStatus().isFinished())) {
                Thread.sleep(1000);
            }

            LOGGER.info("All %s tests finished.".formatted(tests.size()));
        } catch (Exception e) {
            LOGGER.error("Something went wrong while waiting for results.", e);
        }
    }
}