package com.corikachu.yourname.models;

/**
 * Created by loki on 2017. 2. 17..
 */

public class DTOFeed {
    private long id;
    private long ownerId;
    private String title;
    private String content;
    private long createdDate;
    private long updatedDate;

    public DTOFeed() {

    }

    public DTOFeed(long id, long ownerId, String title, String content, long createdDate, long updatedDate) {
        this.id = id;
        this.ownerId = ownerId;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public long getId() {
        return id;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public long getUpdatedDate() {
        return updatedDate;
    }
}
