package com.android.example.github.browser;

import com.android.example.github.browser.repository.data.model.Repository;
import com.android.example.github.browser.repository.data.source.local.ReposDao;
import com.android.example.github.browser.user.data.model.User;
import com.android.example.github.browser.user.data.source.local.UsersDao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * The Room Database that contains the users & repositories tables.
 */
@Database(entities = {User.class, Repository.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UsersDao usersDao();
    public abstract ReposDao reposDao();
}
