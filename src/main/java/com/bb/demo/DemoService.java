package com.bb.demo;

import akka.japi.Option;

/**
 * @author dmytrov
 */
public interface DemoService {
    public void processDontcare();
    public scala.concurrent.Future<String> process();
    public Option<String> processNowPlease();
    public String processNow();
}
