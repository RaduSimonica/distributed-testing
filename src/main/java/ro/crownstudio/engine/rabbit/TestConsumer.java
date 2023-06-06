package ro.crownstudio.engine.rabbit;

import com.rabbitmq.client.*;
import org.testng.TestNG;
import org.testng.xml.Parser;
import org.testng.xml.XmlSuite;
import ro.crownstudio.engine.tests.listeners.SuiteListener;
import ro.crownstudio.engine.tests.listeners.TestListener;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TestConsumer extends DefaultConsumer {

    public TestConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
        System.out.println("Received test ID: " + properties.getHeaders().get("id"));
        try {
            TestNG testNG = new TestNG();
            Parser parser = new Parser(new ByteArrayInputStream(body));
            List<XmlSuite> suites = parser.parseToList();
            testNG.setXmlSuites(suites);
            testNG.setUseDefaultListeners(false);
            testNG.addListener(new TestListener((String) properties.getHeaders().get("id")));
            testNG.addListener(new SuiteListener());
            testNG.run();

            getChannel().basicAck(envelope.getDeliveryTag(), false);

            // TODO: Publish the test results here.
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ups!");
        }
    }
}
