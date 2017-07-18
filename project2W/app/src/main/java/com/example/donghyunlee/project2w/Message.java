package com.example.donghyunlee.project2w;

/**
 * Created by DONGHYUNLEE on 2017-07-15.
 */

public class Message {

    public ContentItem item;
    Message(ContentItem item){
        this.item = item;
    }
    public ContentItem getItem() {
        return item;
    }
}
