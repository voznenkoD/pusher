package com.bb.demo;

import akka.actor.ActorSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by eljetto
 */
@ComponentScan("com.bb.demo")
@EnableWebMvc
@EnableAutoConfiguration
@Configuration
public class AppConfig {
    // the application context is needed to initialize the Akka Spring Extension
    @Autowired
    public ApplicationContext applicationContext;
    /**
     * Actor system singleton for this application.
     */
    @Bean
    public ActorSystem actorSystem() {
        ActorSystem system = ActorSystem.create("BbPresentationDemo");
        SpringExtension.SpringExtProvider.get(system).initialize(applicationContext);
        return system;
    }
}
