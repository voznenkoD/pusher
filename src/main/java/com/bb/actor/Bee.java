package com.bb.actor;

import akka.actor.UntypedActor;
import com.bb.domain.InitMessage;
import com.bb.domain.Item;

import javax.inject.Named;
import org.springframework.context.annotation.Scope;

import java.util.Date;

/**
 * @author dmytrov
 */
@Named("Bee")
@Scope("prototype")
public class Bee extends UntypedActor{

    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof Item){
            System.out.println("I am processing the item");
        } else if (message instanceof InitMessage){
            System.out.println("Let the initialisation begin");
        } else if () {
            getSender().tell("", getSelf());
        }
    }
}
