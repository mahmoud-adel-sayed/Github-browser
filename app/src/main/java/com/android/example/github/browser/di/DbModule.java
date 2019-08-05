package com.android.example.github.browser.di;

import android.app.Application;

import com.android.example.github.browser.AppDatabase;
import com.android.example.github.browser.repository.data.source.local.ReposDao;
import com.android.example.github.browser.user.data.source.local.UsersDao;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

@Module
abstract class DbModule {
    @Provides
    @Singleton
    static AppDatabase provideDb(Application context) {
        return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "TMS.db")
                .build();
    }

    @Provides
    @Singleton
    static UsersDao provideUsersDao(AppDatabase db) {
        return db.usersDao();
    }

    @Provides
    @Singleton
    static ReposDao provideReposDao(AppDatabase db) {
        return db.reposDao();
    }
}
