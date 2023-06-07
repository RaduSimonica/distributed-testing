package ro.crownstudio.engine.tests.receivers;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import ro.crownstudio.engine.rabbit.TestConsumer;

public class TestReceiver extends Receiver {

    @Override
    public void waitForConsume() {
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
