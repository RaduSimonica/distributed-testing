package ro.crownstudio.engine.tests.receivers;

import com.rabbitmq.client.ConnectionFactory;
import ro.crownstudio.config.MainConfig;

public abstract class Receiver {

    protected MainConfig CONFIG = MainConfig.getInstance();
    protected int PREFETCH_COUNT = 1;

    ConnectionFactory factory;

    protected Receiver() {
        factory = new ConnectionFactory();
        factory.setHost(CONFIG.getRabbitHost());
        factory.setUsername(CONFIG.getRabbitUser());
        factory.setPassword(CONFIG.getRabbitPass());
    }

    public abstract void waitForConsume();
}
