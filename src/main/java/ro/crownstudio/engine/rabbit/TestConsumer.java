package ro.crownstudio.engine.rabbit;

import com.google.gson.Gson;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private final static Logger LOGGER = LogManager.getLogger(TestConsumer.class);
    private final MainConfig CONFIG = MainConfig.getInstance();

    public TestConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
        try {
            Test test = new Test(properties.getHeaders().get("id").toString());
            LOGGER.info("Received test with ID: " + test.getId());

            TestNG testNG = new TestNG();
            Parser parser = new Parser(new ByteArrayInputStream(body));
            List<XmlSuite> suites = parser.parseToList();
            testNG.setXmlSuites(suites);
            testNG.setUseDefaultListeners(false);
            testNG.addListener(new TestListener(test));
            testNG.addListener(new SuiteListener());
            testNG.run();

            LOGGER.debug("Test run complete for test ID: " + test.getId());
            getChannel().basicAck(envelope.getDeliveryTag(), false);

            AMQP.BasicProperties props = new AMQP.BasicProperties().builder()
                    .messageId(test.getId())
                    .contentType("application/json")
                    .build();
            RabbitPublisher.publishMessage(new Gson().toJson(test), props, CONFIG.getQueueReceive());
        } catch (Exception e) {
            LOGGER.error("Something went wrong while running test", e);
        }
    }
}
