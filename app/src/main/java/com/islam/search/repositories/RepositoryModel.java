package com.islam.search.repositories;

import com.islam.search.api.ApiClient;
import com.islam.search.api.ApiInterface;
import com.islam.search.model.Repositories;

import retrofit2.Call;
import retrofit2.Callback;

public class RepositoryModel implements IRepositories.IModel {

    Call<Repositories> detailsCall;

    @Override
    public void callRepos(Callback<Repositories> callback, String query) {
        if (detailsCall != null) detailsCall.cancel();
        ApiInterface apiInterface = ApiClient.getInstance().create(ApiInterface.class);
        detailsCall = apiInterface.getRepos(query);
        detailsCall.enqueue(callback);
    }

    @Override
    public void destroy() {
        if (detailsCall != null) {
            detailsCall.cancel();
        }
    }
}
