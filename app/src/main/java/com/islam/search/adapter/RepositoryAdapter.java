package com.islam.search.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.islam.search.R;
import com.islam.search.model.RepositoryData;
import com.islam.search.repositories.IOnclickView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.RepoHolder> {
    private List<RepositoryData> mRepos;
    private IOnclickView<RepositoryData> onClickListener;
    private Context mContext;

    public RepositoryAdapter(Context context, IOnclickView<RepositoryData> onClickListener) {
        this.onClickListener = onClickListener;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RepoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RepoHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_repo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RepoHolder holder, int position) {
        RepositoryData item = mRepos.get(position);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_person)
                .error(R.mipmap.ic_error);
        Glide.with(mContext).load(item.getOwner().getAvatarUrl())
                .apply(options)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.ivAvathar);
        holder.tvName.setText(item.getFullName());
        holder.tvDescription.setText(item.getDescription());
        holder.tvUpdatedAt.setText(String.format("Updated on %s", getDate(item.getUpdatedAt())));
        holder.tvWatchers.setText(String.valueOf(item.getWatchersCount()));
    }

    private String getDate(String time) {

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd yyyy");
        Date date = null;
        try {
            date = inputFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputFormat.format(date);
    }

    @Override
    public int getItemCount() {
        return mRepos == null ? 0 : mRepos.size();
    }

    public void setRepos(List<RepositoryData> repos) {
        this.mRepos = repos;
        notifyDataSetChanged();
    }

    public class RepoHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivAvathar)
        ImageView ivAvathar;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvDescription)
        TextView tvDescription;
        @BindView(R.id.tvWatchers)
        TextView tvWatchers;
        @BindView(R.id.tvUpdatedAt)
        TextView tvUpdatedAt;

        public RepoHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null)
                        onClickListener.onClick(mRepos.get(getAdapterPosition()), itemView);
                }
            });
        }

    }
}
