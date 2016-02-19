package com.bassblog.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinRouter;

/**
 * Created by dmytro on 19/02/16.
 */
public class Master extends UntypedActor {

    private final ActorRef workerRouter;

    public Master(final int nrOfWorkers){
        workerRouter = this.getContext().actorOf(new Props(Worker.class).withRouter(new RoundRobinRouter(nrOfWorkers)),
                "workerRouter");
    }

    @Override
    public void onReceive(Object message) throws Exception {

    }


}
