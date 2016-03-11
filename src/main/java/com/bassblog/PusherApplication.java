package com.bassblog;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import com.bassblog.domain.InitMessage;
import com.bassblog.service.SchedulerService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

import static com.bassblog.SpringExtension.SpringExtProvider;

@SpringBootApplication
@EnableAutoConfiguration
public class PusherApplication {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx =
				new AnnotationConfigApplicationContext();
		ctx.register(Configuration.class);
		ctx.refresh();

		ActorSystem system = ctx.getBean(ActorSystem.class);
		ActorRef worker = system.actorOf(
				SpringExtProvider.get(system).props("Worker"), "worker");

		SchedulerService scheduler = ctx.getBean(SchedulerService.class);
		scheduler.schedule(worker);
	}
}
