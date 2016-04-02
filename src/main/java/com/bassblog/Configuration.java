package com.bassblog;

import akka.actor.*;
import com.relayrides.pushy.apns.ApnsClient;
import com.relayrides.pushy.apns.util.SimpleApnsPushNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

import static com.bassblog.SpringExtension.SpringExtProvider;

/**
 * The application configuration.
 */
@org.springframework.context.annotation.Configuration
@ComponentScan("com.bassblog")
@PropertySource("classpath:application.properties")
public class Configuration {
    // the application context is needed to initialize the Akka Spring Extension
    @Autowired
    public ApplicationContext applicationContext;

    @Autowired
    public ResourceLoader resourceLoader;

    /**
     * Actor system singleton for this application.
     */
    @Bean
    public ActorSystem actorSystem() {
        ActorSystem system = ActorSystem.create("BlogPush");
        SpringExtProvider.get(system).initialize(applicationContext);
        return system;
    }

    @Bean
    public ApnsClient<SimpleApnsPushNotification> apnsClient () throws IOException {
        return new ApnsClient<>(resourceLoader.getResource("classpath:Certificates.p12").getFile(), Constants.PUSH_PASS);
    }
}
