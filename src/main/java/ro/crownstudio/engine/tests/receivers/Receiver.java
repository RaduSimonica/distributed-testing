package ro.crownstudio.engine.tests.receivers;

import ro.crownstudio.config.MainConfig;

public abstract class Receiver {

    protected MainConfig CONFIG = MainConfig.getInstance();
    protected int PREFETCH_COUNT = 1;

    public abstract void waitForConsume();
}
