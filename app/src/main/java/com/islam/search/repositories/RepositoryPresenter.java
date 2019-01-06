package com.islam.search.repositories;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.islam.search.model.Repositories;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryPresenter implements IRepositories.IPresenter {

    IRepositories.IView mView;
    IRepositories.IModel mModel;

    public RepositoryPresenter(IRepositories.IView iView) {
        this.mView = iView;
        mModel = new RepositoryModel();
    }

    @Override
    public void callRepos(String query) {
        mView.showLoading(true);
        mModel.callRepos(new Callback<Repositories>() {
            @Override
            public void onResponse(Call<Repositories> call, Response<Repositories> response) {
                if (mView != null) {
                    mView.onReposLoaded(response.body() == null ? null : response.body().getItems());
                    mView.showLoading(false);
                }
            }

            @Override
            public void onFailure(Call<Repositories> call, Throwable t) {
                if (mView != null) {
                    mView.onReposLoaded(null);
                    mView.showLoading(false);
                }
            }
        }, query);

    }

    @Override
    public void onDestroy() {
        mModel.destroy();
        mView = null;
        mModel = null;
    }

    @Override
    public void doSearchOn(EditText searchView, final ImageButton closeButton) {
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                if (text == null || text.toString().trim().length() == 0) {
                    mView.onReposLoaded(null);
                    closeButton.setVisibility(View.GONE);
                } else {
                    closeButton.setVisibility(View.VISIBLE);
                    callRepos(text.toString().trim());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
