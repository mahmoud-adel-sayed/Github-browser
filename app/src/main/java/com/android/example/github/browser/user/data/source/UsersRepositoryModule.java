package com.android.example.github.browser.user.data.source;

import com.android.example.github.browser.di.qualifier.Local;
import com.android.example.github.browser.di.qualifier.Remote;
import com.android.example.github.browser.user.data.source.local.UsersLocalDataSource;
import com.android.example.github.browser.user.data.source.remote.UsersRemoteDataSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class UsersRepositoryModule {
    @Singleton
    @Binds
    @Local
    abstract UsersDataSource provideUsersLocalDataSource(UsersLocalDataSource dataSource);

    @Singleton
    @Binds
    @Remote
    abstract UsersDataSource provideUsersRemoteDataSource(UsersRemoteDataSource dataSource);
}
