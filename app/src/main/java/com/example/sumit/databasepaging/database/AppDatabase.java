package com.example.sumit.databasepaging.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.sumit.databasepaging.dao.UserDao;
import com.example.sumit.databasepaging.models.User;

import java.io.File;


@Database(entities = {User.class}, version = 1)
abstract public class AppDatabase extends RoomDatabase {
    public static String databaseName = "UserDb";

    private static AppDatabase INSTANCE;

    public abstract UserDao userDao();

    public static final String TAG = "com.example.sumit.db";

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            databaseName = getDatabaseName();
            INSTANCE =
                    Room.databaseBuilder(context, AppDatabase.class, databaseName)
                            // Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
                            // To simplify the exercise, allow queries on the main thread.
                            // Don't do this on a real app!
                            .allowMainThreadQueries()
                            // recreate the database if necessary
                            .fallbackToDestructiveMigration()
                            .build();
        }

        return INSTANCE;
    }

    /**
     *
     */
    public static void invalidate() {
        INSTANCE = null;
    }

    @NonNull
    public static String getDatabaseName() {
        return databaseName;
    }

    public static boolean doesDatabaseExist(Context context) {

        if (context == null) {
            return false;
        }

        String dbName = getDatabaseName();

        if (dbName == null || dbName.isEmpty()) {
            return false;
        }

        File dbFile = context.getDatabasePath(dbName);
        return dbFile != null && dbFile.exists();
    }

    public static boolean deleteDatabase(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile != null && dbFile.delete();
    }

    public static boolean deleteDatabase(Context context) {

        if (context == null) {
            return false;
        }

        String dbName = getDatabaseName();
        Log.d(TAG,"dbName: " + dbName);
        if (dbName == null || dbName.isEmpty()) {
            return false;
        }

        return deleteDatabase(context, dbName);
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
