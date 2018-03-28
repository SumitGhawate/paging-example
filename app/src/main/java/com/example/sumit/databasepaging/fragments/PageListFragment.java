package com.example.sumit.databasepaging.fragments;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sumit.databasepaging.R;
import com.example.sumit.databasepaging.adapter.PagingAdapter;
import com.example.sumit.databasepaging.api.GitHubApi;
import com.example.sumit.databasepaging.database.AppDatabase;
import com.example.sumit.databasepaging.models.User;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PageListFragment extends Fragment {

    //private static final String TAG = "com.example.sumit.PageListFragment";
    private AppDatabase appDatabase;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_page_list, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        appDatabase = AppDatabase.getDatabase(getContext());
        updateData(1, 20);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setListToAdapter();
    }

    private void setListToAdapter() {
        LiveData<List<User>> liveData = appDatabase.userDao().getAllUsers();
        liveData.observeForever(new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                recyclerView.setAdapter(new PagingAdapter(getActivity(), users));
            }
        });
    }

    private void updateData(long pages, int perPage) {
        Call<List<User>> call = GitHubApi.createGitHubService().getUser(pages, perPage);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> users = response.body();
                    if (users == null) {
                        return;
                    }
                    appDatabase.userDao().insertAll(users);
                    String list = new Gson().toJson(users);
                    Log.d(AppDatabase.TAG, "users are " + users.size());
                    setListToAdapter();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.v(AppDatabase.TAG, t.getMessage());
            }
        });
    }
}
