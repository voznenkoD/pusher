package com.bb;

import akka.actor.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ResourceLoader;

import static com.bb.SpringExtension.SpringExtProvider;

/**
 * @author dmytrov
 * The application configuration.
 */
@org.springframework.context.annotation.Configuration
@ComponentScan("com.bb")
public class Configuration {
    // the application context is needed to initialize the Akka Spring Extension
    @Autowired
    public ApplicationContext applicationContext;
    /**
     * Actor system singleton for this application.
     */
    @Bean
    public ActorSystem actorSystem() {
        ActorSystem system = ActorSystem.create("BbPresentationDemo");
        SpringExtProvider.get(system).initialize(applicationContext);
        return system;
    }
}
