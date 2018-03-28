package com.example.sumit.databasepaging.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sumit.databasepaging.R;
import com.example.sumit.databasepaging.models.User;

import java.util.List;

public class PagingAdapter extends RecyclerView.Adapter< PagingAdapter.UserItemViewHolder> {
    private Context context;
    private List<User> users;
    public PagingAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
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
        User user= getItem(position);
        if(user!=null) {
            holder.bindTo(user);
        }
    }

    private User getItem(int position) {
        return users.get(position);
    }

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
        return users.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

}
