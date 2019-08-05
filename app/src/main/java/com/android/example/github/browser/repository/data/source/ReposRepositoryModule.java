package com.android.example.github.browser.repository.data.source;

import com.android.example.github.browser.di.qualifier.Local;
import com.android.example.github.browser.di.qualifier.Remote;
import com.android.example.github.browser.repository.data.source.local.ReposLocalDataSource;
import com.android.example.github.browser.repository.data.source.remote.ReposRemoteDataSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ReposRepositoryModule {
    @Singleton
    @Binds
    @Local
    abstract ReposDataSource provideReposLocalDataSource(ReposLocalDataSource dataSource);

    @Singleton
    @Binds
    @Remote
    abstract ReposDataSource provideReposRemoteDataSource(ReposRemoteDataSource dataSource);
}
