package com.corikachu.yourname.models;

/**
 * Created by loki on 2017. 2. 17..
 */

public class DTORating {
    private long id;
    private long ownerId;
    private int point;

    public DTORating() {

    }

    public DTORating(long id, long ownerId, int point) {
        this.id = id;
        this.ownerId = ownerId;
        this.point = point;
    }

    public long getId() {
        return id;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public int getPoint() {
        return point;
    }
}
