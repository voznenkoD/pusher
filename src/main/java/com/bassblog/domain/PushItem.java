package com.bassblog.domain;

/**
 * Created by eljetto on 4/2/16.
 */
public class PushItem {
    private String selfLink;
    private String title;

    public PushItem() {
    }

    public String getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(String selfLink) {
        this.selfLink = selfLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
