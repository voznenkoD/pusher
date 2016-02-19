package com.bassblog;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;
import com.bassblog.actors.Master;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PusherApplication {

	public static void main(String[] args) {
		SpringApplication.run(PusherApplication.class, args);
		ActorSystem system = ActorSystem.create("PusherSystem");


		// create the master
		ActorRef master = system.actorOf(new Props(new UntypedActorFactory() {
			public UntypedActor create() {
				return new Master();
			}
		}), "master");

		// start the calculation

	}
}
