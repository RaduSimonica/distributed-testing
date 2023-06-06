package ro.crownstudio.engine.tests;

import com.google.common.net.MediaType;
import com.rabbitmq.client.AMQP;
import org.testng.xml.Parser;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import ro.crownstudio.config.MainConfig;
import ro.crownstudio.engine.rabbit.RabbitPublisher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TestDistributor {

    private final MainConfig CONFIG = MainConfig.getInstance();

    public boolean distributeTests(String suiteFileName) {
        try {
            if (!suiteFileName.endsWith(".xml")) {
                suiteFileName += ".xml";
            }
            Parser parser = new Parser(getClass().getClassLoader().getResourceAsStream(suiteFileName));
            for (XmlSuite suite : parser.parse()) {
                for (XmlSuite tempSuite : splitSuite(suite)) {
                    AMQP.BasicProperties props = new AMQP.BasicProperties().builder()
                            .contentType("application/xml")
                            .headers(Map.of("id", UUID.randomUUID().toString()))
                            .build();
                    RabbitPublisher.publishMessage(tempSuite.toXml(), props, CONFIG.getQueueRequest());
                }
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private List<XmlSuite> splitSuite(XmlSuite suite) {
        List<XmlSuite> suites = new ArrayList<>();

        for (XmlTest test : suite.getTests()) {
            XmlSuite tempSuite = new XmlSuite();
            tempSuite.setName("%s_%s".formatted(suite.getName(), test.getName()));

            XmlTest tempTest = new XmlTest(tempSuite);
            tempTest.setName(test.getName());
            tempTest.setXmlClasses(test.getXmlClasses());
            tempTest.setIncludedGroups(test.getIncludedGroups());
            tempTest.setExcludedGroups(test.getExcludedGroups());
            tempTest.setParameters(test.getAllParameters());

            suites.add(tempSuite);
            System.out.println(tempSuite.toXml());
        }

        return suites;
    }
}
