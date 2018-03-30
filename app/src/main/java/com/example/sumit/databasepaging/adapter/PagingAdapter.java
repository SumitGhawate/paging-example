package com.example.sumit.databasepaging.adapter;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sumit.databasepaging.R;
import com.example.sumit.databasepaging.models.User;

import java.util.List;

public class PagingAdapter extends PagedListAdapter<User, PagingAdapter.UserItemViewHolder> {

    private Context context;
    private List<User> users;

    public PagingAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
        //this.users = users;
    }

    @NonNull
    @Override
    public UserItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_user_list, parent, false);
        return new UserItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserItemViewHolder holder, int position) {
        User user = getItem(position);
        if (user != null) {
            holder.bindTo(user);
        }
    }

    /*private User getItem(int position) {
        return users.get(position);
    }*/

    static class UserItemViewHolder extends RecyclerView.ViewHolder {
        TextView userName, userId;

        public UserItemViewHolder(View itemView) {
            super(itemView);
            userId = itemView.findViewById(R.id.userId);
            userName = itemView.findViewById(R.id.userName);
        }

        public void bindTo(User user) {
            userName.setText(user.firstName);
            userId.setText(String.valueOf(user.userId));
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public static final DiffUtil.ItemCallback<User> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<User>() {
                @Override
                public boolean areItemsTheSame(@NonNull User oldUser, @NonNull User newUser) {
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    return oldUser.getUserId() == newUser.getUserId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull User oldUser, @NonNull User newUser) {
                    // NOTE: if you use equals, your object must properly override Object#equals()
                    // Incorrectly returning false here will result in too many animations.
                    return oldUser.equals(newUser);
                }

            };
}
