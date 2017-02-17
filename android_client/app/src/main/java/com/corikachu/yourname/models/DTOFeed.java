package com.corikachu.yourname.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by loki on 2017. 2. 17..
 */

public class DTOFeed implements Parcelable {
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

    private DTOFeed(Parcel in) {
        this.id = in.readLong();
        this.ownerId = in.readLong();
        this.title = in.readString();
        this.content = in.readString();
        this.createdDate = in.readLong();
        this.updatedDate = in.readLong();
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

    public void setId(long id) {
        this.id = id;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public void setTitle(String title) {
        this.title = title;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.id);
        parcel.writeLong(this.ownerId);
        parcel.writeString(this.title);
        parcel.writeString(this.content);
        parcel.writeLong(this.createdDate);
        parcel.writeLong(this.updatedDate);
    }

    public static final Parcelable.Creator<DTOFeed> CREATOR = new Parcelable.Creator<DTOFeed>() {
        public DTOFeed createFromParcel(Parcel in) {
            return new DTOFeed(in);
        }

        public DTOFeed[] newArray(int size) {
            return new DTOFeed[size];
        }
    };
}
