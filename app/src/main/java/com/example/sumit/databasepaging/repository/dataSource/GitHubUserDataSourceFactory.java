package com.example.sumit.databasepaging.repository.dataSource;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import java.util.concurrent.Executor;

public class GitHubUserDataSourceFactory extends DataSource.Factory {

    MutableLiveData<ItemKeyedUserDataSource> mutableLiveData;
    ItemKeyedUserDataSource itemKeyedUserDataSource;
    Executor executor;

    public GitHubUserDataSourceFactory(Executor executor) {
        this.mutableLiveData = new MutableLiveData<ItemKeyedUserDataSource>();
        this.executor = executor;
    }


    @Override
    public DataSource create() {
        itemKeyedUserDataSource = new ItemKeyedUserDataSource(executor);
        mutableLiveData.postValue(itemKeyedUserDataSource);
        return itemKeyedUserDataSource;
    }

    public MutableLiveData<ItemKeyedUserDataSource> getMutableLiveData() {
        return mutableLiveData;
    }

}
