package com.islam.search.details;

import com.islam.search.api.ApiClient;
import com.islam.search.api.ApiInterface;
import com.islam.search.model.Subscribers;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class RepositoryDetailsModel implements IRepositoryDetails.IModel {

    private Call<List<Subscribers>> subscribers;

    @Override
    public void getSubscribers(Callback<List<Subscribers>> callback, String url) {
        ApiInterface apiInterface = ApiClient.getInstance().create(ApiInterface.class);
        subscribers = apiInterface.getSubscribers(url);
        subscribers.enqueue(callback);
    }

    @Override
    public void onDestroy() {
        if (subscribers != null) {
            subscribers.cancel();
        }
    }
}
