package com.bassblog.service;

import com.bassblog.domain.BlogPost;
import com.google.gdata.client.blogger.BloggerService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.Entry;
import com.google.gdata.data.Feed;
import com.google.gdata.client.Query;
import com.google.gdata.util.ServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by dmytro on 19/02/16.
 * main purpose of that service would be to retrieve bass blog to get new messages
 */
@Service
public class BloggerRetrieveService {
    private String feedUri;
    private BloggerService bloggerService;
    private static final String METAFEED_URL = "http://www.blogger.com/feeds/default/blogs";

    private static final String FEED_URI_BASE = "http://www.blogger.com/feeds";
    private static final String POSTS_FEED_URI_SUFFIX = "/posts/default";
    private static final String BLOG_ID = "megaBlogId";
    private static final String USERNAME = "megaBlogId";
    private static final String PASSWORD = "megaBlogId";

    @PostConstruct
    public void init() {
        bloggerService = new BloggerService("exampleCo-exampleApp-1");
        try {
            bloggerService.setUserCredentials(USERNAME, PASSWORD);

            final URL feedUrl = new URL(METAFEED_URL);
            Feed resultFeed = bloggerService.getFeed(feedUrl, Feed.class);

            if (resultFeed.getEntries().size() > 0) {
                Entry entry = resultFeed.getEntries().get(0);
                String blogId = entry.getId().split("blog-")[1];
            } else {
                throw new IOException("User has no blogs!");
            }
            feedUri = FEED_URI_BASE + "/" + BLOG_ID;
        } catch (ServiceException | IOException se) {
            se.printStackTrace();
        }
    }

    public List<BlogPost> getBlogPostsSince(Date lastNotificationTime) {
        List<BlogPost> blogPosts = new LinkedList<>();
        // Create query and submit a request
        URL feedUrl = null;
        try {
            feedUrl = new URL(feedUri + POSTS_FEED_URI_SUFFIX);

            Query myQuery = new Query(feedUrl);
            myQuery.setUpdatedMin(new DateTime(lastNotificationTime.getTime()));
            Feed resultFeed = bloggerService.query(myQuery, Feed.class);
            for (int i = 0; i < resultFeed.getEntries().size(); i++) {
                Entry entry = resultFeed.getEntries().get(i);

            }
        } catch (ServiceException | IOException e) {
            e.printStackTrace();
        }
        return blogPosts;
    }
}
