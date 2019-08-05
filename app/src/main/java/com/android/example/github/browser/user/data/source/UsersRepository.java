package com.android.example.github.browser.user.data.source;

import android.util.Log;

import com.android.example.github.browser.di.qualifier.Local;
import com.android.example.github.browser.di.qualifier.Remote;
import com.android.example.github.browser.user.data.model.User;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

@Singleton
public class UsersRepository implements UsersDataSource {

    private final UsersDataSource mRemoteUsersDataSource;
    private final UsersDataSource mLocalUsersDataSource;

    @Inject
    public UsersRepository(@Remote UsersDataSource remoteUsersDataSource,
                           @Local UsersDataSource localUsersDataSource) {
        mRemoteUsersDataSource = remoteUsersDataSource;
        mLocalUsersDataSource = localUsersDataSource;
    }

    @Override
    public Flowable<List<User>> getAllUsers() {
        return mLocalUsersDataSource.getAllUsers();
    }

    @Override
    public Flowable<User> getUser(String userName) {
        return mRemoteUsersDataSource.getUser(userName)
                .doOnNext(user -> {
                    user.setCreatedAt(System.currentTimeMillis());
                    mLocalUsersDataSource.saveUser(user);
                    Log.e("put in local", user.toString());
                });
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
