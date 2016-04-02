package com.bassblog.service;

import com.bassblog.Constants;
import com.bassblog.domain.PushItem;
import com.bassblog.domain.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by dmytro on 19/02/16.
 * main purpose of that service would be to retrieve bass blog to get new messages
 */
@Service
public class BloggerRetrieveService {


    public List<PushItem> getBlogPostsSince(Date lastNotificationTime) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(buildUrlForPushItemsSince(lastNotificationTime));
        HttpResponse response = client.execute(request);
        Result result = new ObjectMapper().readValue(getStringResponseBody(response), Result.class);
        return result.getItems();
    }

    private String getStringResponseBody(HttpResponse response) throws IOException {
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));
        StringBuilder stringBody = new StringBuilder();
        String line = "";
        while ((line = rd.readLine()) != null) {
            stringBody.append(line);
        }
        //TODO handle empty
        return stringBody.toString();
    }

    private String buildUrlForPushItemsSince(Date date){
        return Constants.BASE_URL + Constants.BLOG_ID + "/posts?fetchBodies=true&fetchImages=false&startDate=" +
                transformDateForUrl(date) + "&fields=items(selfLink%2Ctitle)&key=" + Constants.API_KEY;
    }

    private String transformDateForUrl(Date date){
        try {
            return URLEncoder.encode(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(date), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            //TODO logging of behaviour
            return "2016-03-29T12%3A30%3A00%2B02%3A00";
        }
    }
}
