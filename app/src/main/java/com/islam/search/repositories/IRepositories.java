package com.islam.search.repositories;

import android.widget.EditText;
import android.widget.ImageButton;

import com.islam.search.model.RepositoryData;
import com.islam.search.model.Repositories;

import java.util.List;

import retrofit2.Callback;

public interface IRepositories {

    interface IView {

        void onReposLoaded(List<RepositoryData> repos);

        void showLoading(boolean show);
    }

    interface IModel {
        void callRepos(Callback<Repositories> callback, String query);

        void destroy();
    }

    interface IPresenter {
        void callRepos(String query);

        void onDestroy();

        void doSearchOn(EditText searchView, ImageButton closeButton);

    }
}
