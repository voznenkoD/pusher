package com.bassblog.service;

import com.relayrides.pushy.apns.ApnsClient;
import com.relayrides.pushy.apns.util.ApnsPayloadBuilder;
import com.relayrides.pushy.apns.util.SimpleApnsPushNotification;
import com.relayrides.pushy.apns.util.TokenUtil;
import io.netty.util.concurrent.Future;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by dmytro on 19/02/16.
 */
public class ApplePushSendService {
    @Autowired
    final SimpleApnsPushNotification pushNotification;

    public void init() {

        final ApnsPayloadBuilder payloadBuilder = new ApnsPayloadBuilder();
        payloadBuilder.setAlertBody("Example!");

        final String payload = payloadBuilder.buildWithDefaultMaximumLength();
        final String token = TokenUtil.sanitizeTokenString("<efc7492 bdbd8209>");

        pushNotification = new SimpleApnsPushNotification(token, "com.example.myApp", payload);
    }

}
