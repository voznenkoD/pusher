package com.bb.demo;

import akka.actor.UntypedActor;
import com.bb.demo.InitMessage;
import com.bb.demo.Item;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author dmytrov
 */
@Component("bee")
@Scope("prototype")
public class Bee extends UntypedActor{

    private int counter;

    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof Item){
            System.out.println("I am processing following item : " + message.toString());
            counter++;
        } else if (message instanceof InitMessage){
            System.out.println("Let the initialisation begin");
        } else if (message instanceof Result){
            getSender().tell(counter, getSelf());
        } else {
            unhandled(message);
        }
    }
}
