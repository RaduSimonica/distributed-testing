package ro.crownstudio.engine.rabbit;

import com.google.gson.Gson;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import ro.crownstudio.pojo.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class ResultsConsumer extends DefaultConsumer {

    private final List<Test> tests;

    public ResultsConsumer(Channel channel, List<Test> tests) {
        super(channel);
        this.tests = tests;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
        try {
            System.out.println("Received results ID: " + properties.getMessageId());

            if (tests.stream().anyMatch(x -> x.getId().equals(properties.getMessageId()))) {
                Test receivedTest = new Gson().fromJson(new String(body, StandardCharsets.UTF_8), Test.class);
                tests.stream()
                        .filter(x -> x.equals(receivedTest))
                        .findFirst()
                        .ifPresent(test -> test.setStatus(receivedTest.getStatus()));
                getChannel().basicAck(envelope.getDeliveryTag(), false);
            } else {
                getChannel().basicAck(envelope.getDeliveryTag(), false);
                getChannel().basicReject(envelope.getDeliveryTag(), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ups!");
        }
    }
}
