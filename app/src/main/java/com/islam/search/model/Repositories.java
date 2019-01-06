package com.islam.search.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Repositories {

    @SerializedName("items")
    @Expose
    private List<RepositoryData> items = new ArrayList<RepositoryData>();

    public List<RepositoryData> getItems() {
        return items;
    }

    public void setItems(List<RepositoryData> items) {
        this.items = items;
    }

}

