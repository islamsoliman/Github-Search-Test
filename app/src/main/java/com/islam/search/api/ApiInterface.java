package com.islam.search.api;

import com.islam.search.model.Subscribers;
import com.islam.search.model.Repositories;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {

    @GET("repositories?sort=stars&order=desc&per_page=25")
    Call<Repositories> getRepos(@Query("q") String query);

    @GET
    Call<List<Subscribers>> getSubscribers(@Url String url);
}
