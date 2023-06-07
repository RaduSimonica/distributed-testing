package ro.crownstudio.engine.rabbit;

import com.google.gson.Gson;
import com.rabbitmq.client.*;
import org.testng.TestNG;
import org.testng.xml.Parser;
import org.testng.xml.XmlSuite;
import ro.crownstudio.config.MainConfig;
import ro.crownstudio.engine.tests.listeners.SuiteListener;
import ro.crownstudio.engine.tests.listeners.TestListener;
import ro.crownstudio.pojo.Test;

import java.io.ByteArrayInputStream;
import java.util.List;

public class TestConsumer extends DefaultConsumer {

    private final MainConfig CONFIG = MainConfig.getInstance();

    public TestConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
        try {
            System.out.println("Received test ID: " + properties.getHeaders().get("id"));

            Test test = new Test(properties.getHeaders().get("id").toString());

            TestNG testNG = new TestNG();
            Parser parser = new Parser(new ByteArrayInputStream(body));
            List<XmlSuite> suites = parser.parseToList();
            testNG.setXmlSuites(suites);
            testNG.setUseDefaultListeners(false);
            testNG.addListener(new TestListener(test));
            testNG.addListener(new SuiteListener());
            testNG.run();

            getChannel().basicAck(envelope.getDeliveryTag(), false);

            AMQP.BasicProperties props = new AMQP.BasicProperties().builder()
                    .messageId(test.getId())
                    .contentType("application/json")
                    .build();
            RabbitPublisher.publishMessage(new Gson().toJson(test), props, CONFIG.getQueueReceive());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ups!");
        }
    }
}
