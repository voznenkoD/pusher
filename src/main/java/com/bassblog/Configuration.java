package com.bassblog;

import akka.actor.*;
import com.relayrides.pushy.apns.ApnsClient;
import com.relayrides.pushy.apns.util.SimpleApnsPushNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;

import static com.bassblog.SpringExtension.SpringExtProvider;

/**
 * The application configuration.
 */
@org.springframework.context.annotation.Configuration
public class Configuration {
    // the application context is needed to initialize the Akka Spring Extension
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ResourceLoader resourceLoader;

    /**
     * Actor system singleton for this application.
     */
    @Bean
    public ActorSystem actorSystem() {
        ActorSystem system = ActorSystem.create("BlogPush");
        // initialize the application context in the Akka Spring Extension
        SpringExtProvider.get(system).initialize(applicationContext);
        return system;
    }

    @Bean
    public ApnsClient<SimpleApnsPushNotification> apnsClient () throws IOException {
        return new ApnsClient<>(resourceLoader.getResource("classpath:cert.p12").getFile(), "p12-file-password");
    }
}
