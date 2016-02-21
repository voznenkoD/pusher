package com.bassblog.service;

import akka.actor.ActorRef;
import com.bassblog.domain.InitMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by dmytro on 19/02/16.
 */
@Service
public class SchedulerService {

    @Resource
    ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Value("${service.pollTime}")
    Long servicePollTime;

    /**
     * Schedule the run of the worker
     */
    public void schedule(ActorRef worker) {
        threadPoolTaskScheduler.scheduleWithFixedDelay((Runnable) () -> worker.tell(new InitMessage(), null), servicePollTime);
    }
}
