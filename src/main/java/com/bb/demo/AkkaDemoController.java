package com.bb.demo;

import akka.actor.*;
import akka.pattern.Patterns;
import akka.util.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import scala.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static com.bb.demo.SpringExtension.SpringExtProvider;
import static sun.jvm.hotspot.oops.CellTypeState.ref;

/**
 * @author dmytrov
 */

@RestController
public class AkkaDemoController {

    @Autowired
    ActorSystem actorSystem;

    FiniteDuration duration = FiniteDuration.create(1, TimeUnit.SECONDS);

    private ActorRef initActor() {
        return actorSystem.actorOf(
                SpringExtProvider.get(actorSystem).props("bee"), "bee");
    }

    @RequestMapping(value = "/untyped", method = RequestMethod.GET)
    public String untyped() throws Exception {
        ActorRef ref = initActor();

        try {
            ref.tell(new InitMessage(), ref);
            ref.tell(new Item("http://google.com", "googleItem"),ref);
            ref.tell(new Item("http://yahoo.com", "yahooItem"),ref);
            ref.tell(new Item("http://backbase.com", "backbaseItem"),ref);

            Future<Object> awaitable = Patterns.ask(ref, new Result(), Timeout.durationToTimeout(duration));
            System.out.println(Await.result(awaitable, duration));
            //Or resolve future using Spring DeferredResult
            return "Response";
        } finally {
            actorSystem.stop(ref);
        }
    }

    @RequestMapping(value = "/typed", method = RequestMethod.GET)
    public String typed() throws Exception {
        DemoService demoService = TypedActor.get(actorSystem).typedActorOf(new TypedProps<DemoServiceImpl>(DemoService.class, DemoServiceImpl.class));

        try {
            demoService.processDontcare();
            System.out.println(demoService.processNowPlease());
            System.out.println(demoService.processNow());
            System.out.println(Await.result(demoService.process(), duration));

            return "Response";
        } finally {
            TypedActor.get(actorSystem).stop(demoService);
        }
    }


}
