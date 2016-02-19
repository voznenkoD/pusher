package com.bassblog;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import com.bassblog.domain.InitMessage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static com.bassblog.SpringExtension.SpringExtProvider;

@SpringBootApplication
public class PusherApplication {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx =
				new AnnotationConfigApplicationContext();
		ctx.scan("com.bassblog");
		ctx.refresh();

		ActorSystem system = ctx.getBean(ActorSystem.class);
		ActorRef counter = system.actorOf(
				SpringExtProvider.get(system).props("Worker"), "worker");

        //TODO schedule with quartz
        while(true) {
            counter.tell(new InitMessage(), null);
        }
	}
}