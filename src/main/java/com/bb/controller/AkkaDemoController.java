package com.bb.controller;

import com.bb.domain.InitMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import static com.bb.SpringExtension.SpringExtProvider;

/**
 * @author dmytrov
 */

@Controller
public class AkkaDemoController {

    @Autowired
    ActorSystem actorSystem;

    private ActorRef initActor() {
        return actorSystem.actorOf(
                SpringExtProvider.get(actorSystem).props("Bee"), "bee");
    }

    @RequestMapping("/init")
    public String greeting() {
        ActorRef ref = initActor();
        ref.tell(new InitMessage(), null);
        return ref.path().toStringWithoutAddress();
    }


    @RequestMapping("/process")
    public String greeting() {
        ActorRef ref = initActor();
        ref.tell(new InitMessage(), null);
        return ref.path().toStringWithoutAddress();
    }

}
