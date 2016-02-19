package com.bassblog.actors;

import akka.actor.UntypedActor;
import com.bassblog.domain.BlogPost;
import com.bassblog.service.ApplePushSendService;
import com.bassblog.service.BloggerRetrieveService;
import com.bassblog.service.DBStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by dmytro on 19/02/16.
 */
public class Worker extends UntypedActor{

    @Autowired
    DBStoreService dbStoreService;
    @Autowired
    BloggerRetrieveService bloggerRetrieveService;
    @Autowired
    ApplePushSendService applePushSendService;


    @Override
    public void onReceive(Object message) throws Exception {

        if(message instanceof BlogPost){
            applePushSendService.sendPushNotification((BlogPost) message);
        } else if (message instanceof Date) {
            dbStoreService.storeLastNotificationTime((Date) message);
        } else if (message instanceof ){
            Date lastNotificationTime = dbStoreService.getLastNotificationTime();
            List<BlogPost> posts = bloggerRetrieveService.getBlogPostsSince(lastNotificationTime);
            for (BlogPost post : posts) {
                getSender().tell(post , getSelf());
            }
        }

    }
}
