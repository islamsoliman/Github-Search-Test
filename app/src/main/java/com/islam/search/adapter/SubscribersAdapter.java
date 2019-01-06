package com.islam.search.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.islam.search.R;
import com.islam.search.model.Subscribers;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubscribersAdapter extends RecyclerView.Adapter<SubscribersAdapter.SubscriberViewer> {

    private List<Subscribers> subscribers;
    private Context mContext;

    public SubscribersAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public SubscriberViewer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SubscriberViewer(LayoutInflater.from(mContext)
                .inflate(R.layout.inflate_subscribers, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SubscriberViewer holder, int position) {
        Subscribers subscribers = this.subscribers.get(position);
        holder.tvName.setText(subscribers.getLogin());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.ivAvathar.setTransitionName(subscribers.getLogin());
        }

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_person)
                .error(R.mipmap.ic_error);
        Glide.with(mContext).load(subscribers.getAvatarUrl())
                .apply(options)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.ivAvathar);
    }

    @Override
    public int getItemCount() {
        return subscribers == null ? 0 : subscribers.size();
    }

    public void setSubscribers(List<Subscribers> subscribers) {
        this.subscribers = subscribers;
        notifyDataSetChanged();
    }

    class SubscriberViewer extends RecyclerView.ViewHolder {

        @BindView(R.id.ivAvathar)
        ImageView ivAvathar;
        @BindView(R.id.tvName)
        TextView tvName;

        public SubscriberViewer(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
