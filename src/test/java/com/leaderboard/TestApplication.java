package com.leaderboard;

import com.google.inject.Stage;
import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by ankit.chaudhury on 15/08/17.
 */
@Slf4j
public class TestApplication extends Application<TestConfiguration> {
    private GuiceBundle<TestConfiguration> guiceBundle;

    public static void main(String[] args) throws Exception {
        log.info("Test Application starting");
        new MyApplication().run(args);
        log.info("Test Application up");
    }

    @Override
    public void run(TestConfiguration configuration, Environment environment) throws Exception {

    }

    @Override
    public void initialize(Bootstrap<TestConfiguration> bootstrap) {
        guiceBundle = GuiceBundle.<TestConfiguration>newBuilder()
                .addModule(new TestModule())
                .enableAutoConfig(getClass().getPackage().getName())
                .setConfigClass(TestConfiguration.class).build(Stage.DEVELOPMENT);

        bootstrap.addBundle(guiceBundle);
    }
}
