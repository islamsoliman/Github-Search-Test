package com.islam.search;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.islam.search.adapter.RepositoryAdapter;
import com.islam.search.model.RepositoryData;
import com.islam.search.repositories.IOnclickView;
import com.islam.search.repositories.IRepositories;
import com.islam.search.repositories.RepositoryPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchRepositoryActivity extends AppCompatActivity implements IRepositories.IView {
    @BindView(R.id.rvRepoList)
    RecyclerView rvRepoList;
    @BindView(R.id.vProgress)
    ProgressBar vProgress;
    @BindView(R.id.ibClose)
    ImageButton ibClose;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.vNoData)
    View vNoData;

    private IRepositories.IPresenter mRepoPresenter;
    private RepositoryAdapter mRepositoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_repo);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRepoPresenter = new RepositoryPresenter(this);

        mRepoPresenter.doSearchOn(etSearch, ibClose);

        rvRepoList.setLayoutManager(new LinearLayoutManager(this));
        mRepositoryAdapter = new RepositoryAdapter(this, new IOnclickView<RepositoryData>() {
            @Override
            public void onClick(RepositoryData item, View view) {
                Intent repoDetailsIntent = new Intent(SearchRepositoryActivity.this, RepositoryDetails.class);
                repoDetailsIntent.putExtra(RepositoryDetails.KEY_REPO_DATA, item);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions
                            .makeSceneTransitionAnimation(SearchRepositoryActivity.this, view, item.getOwner().getAvatarUrl());
                    startActivity(repoDetailsIntent, options.toBundle());
                } else {
                    startActivity(repoDetailsIntent);
                }
            }
        });
        rvRepoList.setAdapter(mRepositoryAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!Utils.isOnline(this)) {
            Utils.showToast(this, getString(R.string.check_internet_connection));
        }
    }

    @OnClick(R.id.ibClose)
    public void onClick(View view) {
        etSearch.getText().clear();
    }

    @Override
    public void onReposLoaded(List<RepositoryData> repos) {
        mRepositoryAdapter.setRepos(repos);
        vNoData.setVisibility(repos == null ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showLoading(boolean show) {
        vProgress.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            mRepoPresenter.onDestroy();
        }
    }

}
