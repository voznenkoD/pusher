package com.bb.demo;

import akka.pattern.Patterns;
import akka.util.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import scala.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static com.bb.demo.SpringExtension.SpringExtProvider;

/**
 * @author dmytrov
 */

@RestController
public class AkkaDemoController {

    @Autowired
    ActorSystem actorSystem;

    private ActorRef initActor() {
        return actorSystem.actorOf(
                SpringExtProvider.get(actorSystem).props("bee"), "bee");
    }

    @RequestMapping(value = "/process", method = RequestMethod.GET)
    public String init() throws Exception {

        try {
            ActorRef ref = initActor();

            ref.tell(new InitMessage(), ref);
            ref.tell(new InitMessage(), ref);
            ref.tell(new InitMessage(), ref);
            ref.tell(new Item("http://google.com", "googleItem"),ref);

            FiniteDuration duration = FiniteDuration.create(1, TimeUnit.SECONDS);
            Future<Object> awaitable = Patterns.ask(ref, new Result(), Timeout.durationToTimeout(duration));
            System.out.println(Await.result(awaitable, duration));

            return "Response";
        } finally {
            actorSystem.terminate();
            Await.result(actorSystem.whenTerminated(), Duration.Inf());
        }
    }
}
