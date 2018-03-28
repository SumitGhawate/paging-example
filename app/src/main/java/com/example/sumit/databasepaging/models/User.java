package com.example.sumit.databasepaging.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class User {

    /*public static DiffCallback<User> DIFF_CALLBACK = new DiffCallback<User>() {
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.userId == newItem.userId;
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.equals(newItem);
        }
    };*/

    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    public long userId;

    @SerializedName("login")
    @ColumnInfo(name = "first_name")
    public String firstName;
    public String address;

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        User user = (User) obj;

        return user.userId == this.userId && user.firstName == this.firstName;
    }
}
