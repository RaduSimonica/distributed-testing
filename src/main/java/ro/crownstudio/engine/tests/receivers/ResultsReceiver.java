package ro.crownstudio.engine.tests.receivers;

import com.rabbitmq.client.*;
import ro.crownstudio.engine.rabbit.ResultsConsumer;
import ro.crownstudio.pojo.Test;

import java.util.List;

public class ResultsReceiver extends Receiver {

    private final List<Test> tests;

    public ResultsReceiver(List<Test> tests) {
        super();
        this.tests = tests;
    }

    @Override
    public void waitForConsume() {
        try (Connection connection = factory.newConnection()) {
            Channel channel = connection.createChannel();
            channel.queueDeclare(CONFIG.getQueueReceive(), false, false, false, null);
            channel.basicQos(PREFETCH_COUNT);

            Consumer consumer = new ResultsConsumer(channel, tests);
            channel.basicConsume(CONFIG.getQueueReceive(), false, consumer);

            System.out.println("Waiting for results.");

            while (!tests.stream().allMatch(x -> x.getStatus().isFinished())) {
                Thread.sleep(1000);
            }

            System.out.println("All %s tests finished.".formatted(tests.size()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}