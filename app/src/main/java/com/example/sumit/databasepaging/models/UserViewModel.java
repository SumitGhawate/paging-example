package com.example.sumit.databasepaging.models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.example.sumit.databasepaging.dao.UserDao;

public class UserViewModel extends ViewModel {
    public final LiveData<PagedList<User>> usersList;
    public UserViewModel(UserDao userDao) {
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder()).setEnablePlaceholders(true)
                        // .setPrefetchDistance(10)
                        .setPageSize(5).build();

        usersList = (new LivePagedListBuilder(userDao.usersByFirstName(), pagedListConfig))
                .build();
        /*usersList = new LivePagedListBuilder<>(
                userDao.usersByFirstName(), *//* page size *//* 10).build();*/
    }

}
