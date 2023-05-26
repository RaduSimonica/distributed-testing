package ro.crownstudio.engine.rabbit;

import com.rabbitmq.client.*;
import org.testng.TestNG;
import org.testng.xml.Parser;
import org.testng.xml.XmlSuite;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TestConsumer extends DefaultConsumer {

    public TestConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
        System.out.println(new String(body, StandardCharsets.UTF_8));
        try {
            TestNG testNG = new TestNG();
            Parser parser = new Parser(new ByteArrayInputStream(body));
            List<XmlSuite> suites = parser.parseToList();
            testNG.setXmlSuites(suites);
            testNG.run();

            getChannel().basicAck(envelope.getDeliveryTag(), false);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ups!");
        }
    }
}
