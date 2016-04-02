package com.bassblog.domain;

import java.util.List;

/**
 * Created by eljetto on 4/2/16.
 */
public class Result {
    private List<PushItem> items;

    public Result() {
    }

    public List<PushItem> getItems() {
        return items;
    }

    public void setItems(List<PushItem> items) {
        this.items = items;
    }
}
