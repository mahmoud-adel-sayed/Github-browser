package com.android.example.github.browser.user.data.source.remote;

import com.android.example.github.browser.user.data.model.User;
import com.android.example.github.browser.user.data.source.UsersDataSource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * Implementation of the data source as a server.
 */
@Singleton
public class UsersRemoteDataSource implements UsersDataSource {

    private final UsersApiInterface mApiInterface;

    @Inject
    public UsersRemoteDataSource(UsersApiInterface apiInterface) {
        mApiInterface = apiInterface;
    }

    @Override
    public Flowable<List<User>> getAllUsers() {
        return null;
    }

    @Override
    public Flowable<User> getUser(String userName) {
        return mApiInterface.getUser(userName);
    }

    @Override
    public Flowable<User> getUserById(long id) {
        return null;
    }

    @Override
    public long saveUser(User user) {
        return 0;
    }

    @Override
    public Completable deleteUser(long id) {
        return null;
    }

    @Override
    public Maybe<Integer> deleteUsers() {
        return null;
    }
}
