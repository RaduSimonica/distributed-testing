package ro.crownstudio.engine.rabbit;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RabbitPublisher {

    private final static Logger LOGGER = LogManager.getLogger(RabbitPublisher.class);

    public static void publishMessage(String message, AMQP.BasicProperties props, String queue) {
        try (Connection conn = RabbitConnector.getInstance().getFactory().newConnection()) {
            Channel channel = conn.createChannel();
            channel.queueDeclare(queue, false, false, false, null);
            channel.basicPublish("", queue, props, message.getBytes());

            LOGGER.debug("Successfully sent message: %s to queue: %s".formatted(message, queue));
        } catch (Exception e) {
            LOGGER.error("Failed to send message %s to queue: %s".formatted(message, queue), e);
        }
    }
}
