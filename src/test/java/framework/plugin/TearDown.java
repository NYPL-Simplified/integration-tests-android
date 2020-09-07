package framework.plugin;

import aquality.appium.mobile.application.AqualityServices;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventHandler;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestRunFinished;
import io.cucumber.plugin.event.TestRunStarted;

public class TearDown implements ConcurrentEventListener {

    private EventHandler<TestRunStarted> setup = event -> {
        // setup static method(-s) here
    };

    private EventHandler<TestRunFinished> teardown = event -> {
        AqualityServices.getLogger().info("Quitting session");
        AqualityServices.getApplication().quit();
    };

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestRunStarted.class, setup);
        publisher.registerHandlerFor(TestRunFinished.class, teardown);
    }
}
