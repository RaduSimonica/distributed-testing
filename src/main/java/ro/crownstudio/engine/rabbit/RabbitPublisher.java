package ro.crownstudio.engine.rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import ro.crownstudio.config.MainConfig;

public class RabbitPublisher {

    private final static MainConfig CONFIG = MainConfig.getInstance();

    public static void publishMessage(String message, String queue) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(CONFIG.getRabbitHost());
        factory.setUsername(CONFIG.getRabbitUser());
        factory.setPassword(CONFIG.getRabbitPass());

        try (Connection conn = factory.newConnection()) {
            Channel channel = conn.createChannel();
            channel.queueDeclare(queue, false, false, false, null);
            channel.basicPublish("", queue, null, message.getBytes());
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to send message %s to queue: %s".formatted(message, queue),
                    e
            );
        }
    }
}
