package com.android.example.github.browser.user.data.source;

import com.android.example.github.browser.user.data.model.User;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * Main entry point for accessing users data.
 */
public interface UsersDataSource {
    Flowable<List<User>> getAllUsers();
    Flowable<User> getUser(@NonNull String userName);
    Flowable<User> getUserById(long id);
    long saveUser(@NonNull User user);
    Completable deleteUser(long id);
    Maybe<Integer> deleteUsers();
}
