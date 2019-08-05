package com.android.example.github.browser.user.data.source.local;

import com.android.example.github.browser.user.data.model.User;
import com.android.example.github.browser.user.data.source.UsersDataSource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * Concrete implementation of a data source as a db.
 */
@Singleton
public class UsersLocalDataSource implements UsersDataSource {

    private final UsersDao mUsersDao;

    @Inject
    public UsersLocalDataSource(UsersDao usersDao) {
        mUsersDao = usersDao;
    }

    @Override
    public Flowable<List<User>> getAllUsers() {
        return mUsersDao.getAllUsers();
    }

    @Override
    public Flowable<User> getUser(String userName) {
        return mUsersDao.getUser(userName);
    }

    @Override
    public Flowable<User> getUserById(long id) {
        return mUsersDao.getUserById(id);
    }

    @Override
    public long saveUser(User user) {
        return mUsersDao.insert(user);
    }

    @Override
    public Completable deleteUser(long id) {
        return mUsersDao.deleteUser(id);
    }

    @Override
    public Maybe<Integer> deleteUsers() {
        return mUsersDao.deleteUsers();
    }
}
