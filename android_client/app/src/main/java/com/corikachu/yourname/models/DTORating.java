package com.corikachu.yourname.models;

/**
 * Created by loki on 2017. 2. 17..
 */

public class DTORating {
    private long feedId;
    private long suggestId;
    private int point;

    public DTORating() {

    }

    public DTORating(long feedId, long suggestId, int point) {
        this.feedId = feedId;
        this.suggestId = suggestId;
        this.point = point;
    }

    public long getFeedId() {
        return feedId;
    }

    public long getSuggestId() {
        return suggestId;
    }

    public int getPoint() {
        return point;
    }

    public void setFeedId(long feedId) {
        this.feedId = feedId;
    }

    public void setSuggestId(long suggestId) {
        this.suggestId = suggestId;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
