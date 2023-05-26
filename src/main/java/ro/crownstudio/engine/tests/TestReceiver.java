package ro.crownstudio.engine.tests;

import com.rabbitmq.client.*;
import ro.crownstudio.config.MainConfig;
import ro.crownstudio.engine.rabbit.TestConsumer;

public class TestReceiver {

    private final static MainConfig CONFIG = MainConfig.getInstance();
    private final static int PREFETCH_COUNT = 1;

    public static void waitForTests() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(CONFIG.getRabbitHost());
        factory.setUsername(CONFIG.getRabbitUser());
        factory.setPassword(CONFIG.getRabbitPass());

        try (Connection connection = factory.newConnection()) {
            Channel channel = connection.createChannel();
            channel.queueDeclare(CONFIG.getQueueRequest(), false, false, false, null);
            channel.basicQos(PREFETCH_COUNT);
            Consumer consumer = new TestConsumer(channel);
            channel.basicConsume(CONFIG.getQueueRequest(), false, consumer);

            System.out.println("Waiting for messages. To exit, press CTRL+C");

            while (true) {
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
