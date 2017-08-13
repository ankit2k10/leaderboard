package com.leaderboard;

/**
 * Created by ankit.chaudhury on 13/08/17.
 */

import com.google.inject.Stage;
import com.hubspot.dropwizard.guice.GuiceBundle;
import com.leaderboard.config.ApplicationConfiguration;
import com.leaderboard.resources.HelloResource;
import com.leaderboard.resources.LeaderBoardResource;
import com.leaderboard.resources.UserResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyApplication extends Application<ApplicationConfiguration> {

    private GuiceBundle<ApplicationConfiguration> guiceBundle;

    public static void main(String[] args) throws Exception {
        log.info("Application starting");
        new MyApplication().run(args);
        log.info("Application up");
    }

    @Override
    public void run(ApplicationConfiguration configuration, Environment environment) throws Exception {
        environment.jersey().register(new HelloResource());
        environment.jersey().register(new UserResource());
        environment.jersey().register(new LeaderBoardResource());
    }

    @Override
    public void initialize(Bootstrap<ApplicationConfiguration> bootstrap) {
        guiceBundle = GuiceBundle.<ApplicationConfiguration>newBuilder()
                .addModule(new ApplicationModule())
                .enableAutoConfig(getClass().getPackage().getName())
                .setConfigClass(ApplicationConfiguration.class).build(Stage.DEVELOPMENT);

        bootstrap.addBundle(guiceBundle);
    }
}
