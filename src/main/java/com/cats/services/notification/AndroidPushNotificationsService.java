package com.cats.services.notification;

import com.cats.model.FirebaseResponse;
import com.cats.model.HeaderRequestInterceptor;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by andrey on 24.02.17.
 */

@Service
public class AndroidPushNotificationsService {

    private static final String FIREBASE_SERVER_KEY = "FIREBASE_SERVER_KEY";

    private static final Logger LOG = LoggerFactory.getLogger(AndroidPushNotificationsService.class);

    private CompletableFuture<FirebaseResponse> send(HttpEntity<String> entity) {

        RestTemplate restTemplate = new RestTemplate();

        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
        interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
        restTemplate.setInterceptors(interceptors);

        FirebaseResponse firebaseResponse = restTemplate.postForObject("https://fcm.googleapis.com/fcm/send", entity, FirebaseResponse.class);

        return CompletableFuture.completedFuture(firebaseResponse);
    }

    @Async
    public void sendNotification(String msg, String instanceId) {
        if (instanceId == null || instanceId.isEmpty()) {
            return;
        }

        JSONObject body = new JSONObject();
        body.put("to", instanceId);
        body.put("priority", "high");

        JSONObject notification = new JSONObject();
        notification.put("body", msg);
        body.put("notification", notification);

        HttpEntity<String> request = new HttpEntity<>(body.toString());

        CompletableFuture<FirebaseResponse> pushNotification = send(request);
        CompletableFuture.allOf(pushNotification).join();

        try {
            FirebaseResponse firebaseResponse = pushNotification.get();
            if (firebaseResponse.getSuccess() == 1) {
                LOG.info("push notification sent ok!");
            } else {
                LOG.error("error sending push notifications: " + firebaseResponse.toString());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
