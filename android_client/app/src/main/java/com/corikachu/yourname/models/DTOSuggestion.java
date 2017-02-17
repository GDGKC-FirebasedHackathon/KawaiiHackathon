package com.corikachu.yourname.models;

/**
 * Created by loki on 2017. 2. 17..
 */

public class DTOSuggestion {
    private long id;
    private long feedId;
    private String content;
    private long createdDate;
    private long updatedDate;

    public DTOSuggestion() {

    }

    public DTOSuggestion(long id, long feedId, String title, String content, long createdDate, long updatedDate) {
        this.id = id;
        this.feedId = feedId;
        this.content = content;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public long getId() {
        return id;
    }

    public long getFeedId() {
        return feedId;
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

    public void setId(long id) {
        this.id = id;
    }

    public void setFeedId(long feedId) {
        this.feedId = feedId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public void setUpdatedDate(long updatedDate) {
        this.updatedDate = updatedDate;
    }
}
