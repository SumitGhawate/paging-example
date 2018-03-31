package com.example.sumit.databasepaging.repository.dataSource;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.ItemKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.sumit.databasepaging.api.GitHubApi;
import com.example.sumit.databasepaging.api.GitHubService;
import com.example.sumit.databasepaging.models.User;
import com.example.sumit.databasepaging.repository.NetworkState;
import com.example.sumit.databasepaging.repository.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemKeyedUserDataSource extends ItemKeyedDataSource<Long, User> {

    public static final String TAG = "ItemKeyedUserDataSource";
    GitHubService gitHubService;
    LoadInitialParams<Long> initialParams;
    LoadParams<Long> afterParams;
    private MutableLiveData networkState;
    private MutableLiveData initialLoading;
    private Executor retryExecutor;

    public ItemKeyedUserDataSource(Executor retryExecutor) {
        gitHubService = GitHubApi.createGitHubService();
        networkState = new MutableLiveData();
        initialLoading = new MutableLiveData();
        this.retryExecutor = retryExecutor;
    }

    public MutableLiveData getNetworkState() {
        return networkState;
    }

    public MutableLiveData getInitialLoading() {
        return initialLoading;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull final LoadInitialCallback<User> callback) {
        Log.i(TAG, "Loading Rang " + 1 + " Count " + params.requestedLoadSize);
        final List<User> gitHubUser = new ArrayList();
        initialParams = params;
        initialLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);
        gitHubService.getUser(1, params.requestedLoadSize).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    gitHubUser.addAll(response.body());
                    callback.onResult(gitHubUser);
                    initialLoading.postValue(NetworkState.LOADED);
                    networkState.postValue(NetworkState.LOADED);
                    initialParams = null;
                } else {
                    Log.e("API CALL", response.message());
                    initialLoading.postValue(new NetworkState(Status.FAILED, response.message()));
                    networkState.postValue(new NetworkState(Status.FAILED, response.message()));
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                String errorMessage;
                errorMessage = t.getMessage();
                if (t == null) {
                    errorMessage = "unknown error";
                }
                networkState.postValue(new NetworkState(Status.FAILED, errorMessage));
            }
        });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<User> callback) {
        Log.i(TAG, "Loading Rang " + params.key + " Count " + params.requestedLoadSize);
        List<User> gitHubUser = new ArrayList();
        afterParams = params;

        networkState.postValue(NetworkState.LOADING);
        gitHubService.getUser(params.key, params.requestedLoadSize).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    gitHubUser.addAll(response.body());
                    callback.onResult(gitHubUser);
                    networkState.postValue(NetworkState.LOADED);
                    afterParams = null;
                } else {
                    networkState.postValue(new NetworkState(Status.FAILED, response.message()));
                    Log.e("API CALL", response.message());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                String errorMessage;
                errorMessage = t.getMessage();
                if (t == null) {
                    errorMessage = "unknown error";
                }
                networkState.postValue(new NetworkState(Status.FAILED, errorMessage));
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<User> callback) {

    }

    @NonNull
    @Override
    public Long getKey(@NonNull User item) {
        return item.getUserId();
    }
}
