package com.islam.search;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.islam.search.adapter.SubscribersAdapter;
import com.islam.search.details.IRepositoryDetails;
import com.islam.search.details.RepositoryDetailsPresenter;
import com.islam.search.model.Subscribers;
import com.islam.search.model.RepositoryData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RepositoryDetails extends AppCompatActivity implements IRepositoryDetails.IView {

    @BindView(R.id.ivAvathar)
    ImageView ivAvathar;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.rvSubscribers)
    RecyclerView rvSubscribers;
    @BindView(R.id.tvProjectLink)
    TextView tvProjectLink;
    @BindView(R.id.vProgress)
    ProgressBar vProgress;
    private IRepositoryDetails.IPresenter mPresenter;
    private SubscribersAdapter mSubscribersAdapter;
    private RepositoryData mRepoDetails;

    public final static String KEY_REPO_DATA = "REPO_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_details);

        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RepositoryDetails.super.onBackPressed();
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            mPresenter = new RepositoryDetailsPresenter(this);

            mRepoDetails = intent.getParcelableExtra(KEY_REPO_DATA);
            applyRepoData(mRepoDetails);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ivAvathar.setTransitionName(mRepoDetails.getOwner().getAvatarUrl());
            }

            mPresenter.getSubscribers(mRepoDetails.getSubscribersUrl());

        }

        mSubscribersAdapter = new SubscribersAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvSubscribers.setLayoutManager(linearLayoutManager);
        rvSubscribers.setNestedScrollingEnabled(false);
        rvSubscribers.setAdapter(mSubscribersAdapter);
    }

    private void applyRepoData(RepositoryData data) {
        if (data == null) return;
        tvName.setText(data.getName());
        tvDescription.setText(data.getDescription());
        tvProjectLink.setText(data.getHtmlUrl());
        if (!isFinishing()) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.ic_person)
                    .error(R.mipmap.ic_error);
            Glide.with(this).load(data.getOwner().getAvatarUrl())
                    .apply(options)
                    .apply(RequestOptions.circleCropTransform())
                    .into(ivAvathar);
        }
    }

    @OnClick(R.id.tvProjectLink)
    public void onClick(View view) {
        if (mRepoDetails != null) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mRepoDetails.getHtmlUrl()));
            browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            browserIntent.setPackage("com.android.chrome");
            if (browserIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(browserIntent);
            } else {
                browserIntent.setPackage(null);
                startActivity(browserIntent);
            }
        }
    }


    @Override
    public void onSubscribersLoaded(List<Subscribers> subscribers) {
        mSubscribersAdapter.setSubscribers(subscribers);
    }

    @Override
    public void showLoading(boolean show) {
        vProgress.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            mPresenter.onDestroy();
        }
    }
}
