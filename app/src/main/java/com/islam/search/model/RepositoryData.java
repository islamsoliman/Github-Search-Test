package com.islam.search.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RepositoryData implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("full_name")
    @Expose
    private String fullName;

    @SerializedName("html_url")
    @Expose
    private String htmlUrl;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("watchers_count")
    @Expose
    private Integer watchersCount;

    @SerializedName("subscribers_url")
    @Expose
    private String subscribersUrl;
    @SerializedName("owner")
    @Expose
    private Owner owner;

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getSubscribersUrl() {
        return subscribersUrl;
    }

    public void setSubscribersUrl(String subscribersUrl) {
        this.subscribersUrl = subscribersUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getWatchersCount() {
        return watchersCount;
    }

    public void setWatchersCount(Integer watchersCount) {
        this.watchersCount = watchersCount;
    }

    class Woner {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.fullName);
        dest.writeString(this.htmlUrl);
        dest.writeString(this.description);
        dest.writeString(this.url);
        dest.writeString(this.updatedAt);
        dest.writeValue(this.watchersCount);
        dest.writeString(this.subscribersUrl);
        dest.writeParcelable(this.owner, flags);
    }

    public RepositoryData() {
    }

    protected RepositoryData(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.fullName = in.readString();
        this.htmlUrl = in.readString();
        this.description = in.readString();
        this.url = in.readString();
        this.updatedAt = in.readString();
        this.watchersCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.subscribersUrl = in.readString();
        this.owner = in.readParcelable(Owner.class.getClassLoader());
    }

    public static final Creator<RepositoryData> CREATOR = new Creator<RepositoryData>() {
        @Override
        public RepositoryData createFromParcel(Parcel source) {
            return new RepositoryData(source);
        }

        @Override
        public RepositoryData[] newArray(int size) {
            return new RepositoryData[size];
        }
    };
}

