package com.bb.demo;

/**
 * @author dmytrov
 */
public class Item {
    private String link;
    private String title;

    public Item() {
    }

    public Item(String link, String title) {
        this.link = link;
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return "Item{" +
                "link='" + link + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
