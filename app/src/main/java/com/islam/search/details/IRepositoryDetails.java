package com.islam.search.details;


import com.islam.search.model.Subscribers;

import java.util.List;

import retrofit2.Callback;

public interface IRepositoryDetails {
    interface IView {

        void onSubscribersLoaded(List<Subscribers> subscribers);

        void showLoading(boolean show);
    }

    interface IModel {
        void getSubscribers(Callback<List<Subscribers>> callback, String url);

        void onDestroy();
    }

    interface IPresenter {
        void getSubscribers(String url);

        void onDestroy();
    }
}
