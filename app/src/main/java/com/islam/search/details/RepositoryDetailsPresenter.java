package com.islam.search.details;

import com.islam.search.model.Subscribers;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryDetailsPresenter implements IRepositoryDetails.IPresenter {

    private IRepositoryDetails.IView mDetailsView;
    private IRepositoryDetails.IModel mDetailsModel;

    public RepositoryDetailsPresenter(IRepositoryDetails.IView details) {
        this.mDetailsView = details;
        mDetailsModel = new RepositoryDetailsModel();
    }

    @Override
    public void getSubscribers(String url) {
        mDetailsView.showLoading(true);
        mDetailsModel.getSubscribers(new Callback<List<Subscribers>>() {
            @Override
            public void onResponse(Call<List<Subscribers>> call, Response<List<Subscribers>> response) {
                if (mDetailsView != null) {
                    mDetailsView.onSubscribersLoaded(response.body());
                    mDetailsView.showLoading(false);
                }
            }

            @Override
            public void onFailure(Call<List<Subscribers>> call, Throwable t) {
                if (mDetailsView != null) {
                    mDetailsView.onSubscribersLoaded(null);
                    mDetailsView.showLoading(false);
                }
            }
        }, url);

    }

    @Override
    public void onDestroy() {
        mDetailsModel.onDestroy();
        mDetailsView = null;
        mDetailsModel = null;
    }
}
