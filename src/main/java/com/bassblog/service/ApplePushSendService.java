package com.bassblog.service;

import com.bassblog.domain.BlogPost;
import com.relayrides.pushy.apns.ApnsClient;
import com.relayrides.pushy.apns.ClientNotConnectedException;
import com.relayrides.pushy.apns.PushNotificationResponse;
import com.relayrides.pushy.apns.util.ApnsPayloadBuilder;
import com.relayrides.pushy.apns.util.SimpleApnsPushNotification;
import com.relayrides.pushy.apns.util.TokenUtil;
import io.netty.util.concurrent.Future;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutionException;

/**
 * Created by dmytro on 19/02/16.
 */
@Service
public class ApplePushSendService {
    @Autowired
    ApnsClient<SimpleApnsPushNotification> apnsClient;
    SimpleApnsPushNotification pushNotification;
    ApnsPayloadBuilder payloadBuilder;

    @PostConstruct
    public void init() {
        final Future<Void> connectFuture = apnsClient.connect(ApnsClient.DEVELOPMENT_APNS_HOST);
        try {
            connectFuture.await();
        } catch (InterruptedException e) {
            //TODO research and do proper handling
        }
    }

    public void shutdown() {
        final Future<Void> disconnectFuture = apnsClient.disconnect();
        try {
            disconnectFuture.await();
        } catch (InterruptedException e) {
            //TODO proper handler
        }
    }


    public void sendPushNotif(BlogPost blogPost) {
        ApnsPayloadBuilder  payloadBuilder = new ApnsPayloadBuilder();
        //TODO proper payloadBuilder
        payloadBuilder.setAlertBody(blogPost.toString());
        final String payload = payloadBuilder.buildWithDefaultMaximumLength();
        final String token = TokenUtil.sanitizeTokenString("<efc7492 bdbd8209>");
        final SimpleApnsPushNotification pushNotification = new SimpleApnsPushNotification(token, "com.example.myApp", payload);
        final Future<PushNotificationResponse<SimpleApnsPushNotification>> sendNotificationFuture =
                apnsClient.sendNotification(pushNotification);
        try {
            final PushNotificationResponse<SimpleApnsPushNotification> pushNotificationReponse;
            pushNotificationReponse = sendNotificationFuture.get();
            if (pushNotificationReponse.isAccepted()) {
                //if we need to do smth in case of success sending

            } else {
                //or we need to handle somehow the rejection
                pushNotificationReponse.getRejectionReason();
                if (pushNotificationReponse.getTokenInvalidationTimestamp() != null) {
                    //one of the rejection reasons
                    pushNotificationReponse.getTokenInvalidationTimestamp();
                }
            }
        } catch (final ExecutionException e) {
            System.err.println("Failed to send push notification.");
            e.printStackTrace();
            if (e.getCause() instanceof ClientNotConnectedException) {
                //trying to reconnect
                try {
                    apnsClient.getReconnectionFuture().await();
                    //successa reconnection
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            //in case of interuption of send notification
        }
    }

}
