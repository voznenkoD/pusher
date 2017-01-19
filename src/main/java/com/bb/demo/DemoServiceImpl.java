package com.bb.demo;

import akka.dispatch.Futures;
import akka.japi.Option;
import scala.concurrent.Future;
/**
 * @author dmytrov
 */
public class DemoServiceImpl implements DemoService {

    //sendOneWay or Fire and forget
    public void processDontcare() {
        System.out.println("Nobody cares");
    }

    //request-reply-with-future
    public Future<String> process() {
        String successa = "Future is near";
        return Futures.successful(successa+"!");
    }

    //request-reply optional
    public Option<String> processNowPlease() {
        return Option.some("Now, please!");
    }

    //request-reply
    public String processNow() {
        return "NOW!!!!";
    }
}
