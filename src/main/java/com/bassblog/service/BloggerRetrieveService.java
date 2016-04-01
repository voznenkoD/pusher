package com.bassblog.service;

import com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential;
import com.google.api.services.blogger.Blogger;
import com.google.api.services.blogger.BloggerScopes;
import com.google.api.services.blogger.model.Blog;
import com.google.api.services.blogger.model.Post;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by dmytro on 19/02/16.
 * main purpose of that service would be to retrieve bass blog to get new messages
 */
@Service
public class BloggerRetrieveService {
    public Blogger blogger = null;
    public Blog blog;
    static final String API_KEY = "";
    private static final String blogURL = "";


    @PostConstruct
    public void init() {
            AppIdentityCredential credential = new AppIdentityCredential(Arrays.asList(BloggerScopes.BLOGGER));
            this.blogger = new Blogger.Builder(new UrlFetchTransport(), new JacksonFactory(), credential).setApplicationName("pushNotifier").build();
    }

    public List<Post> getBlogPostsSince(Date lastNotificationTime) throws IOException {
        Blogger.Blogs.GetByUrl request = blogger.blogs().getByUrl(blogURL);
        this.blog = request.setKey(API_KEY).execute();
        List<Post> posts = blog.getPosts().getItems();
        return posts;
    }
}
