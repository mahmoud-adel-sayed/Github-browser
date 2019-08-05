package com.android.example.github.browser.user.data.source.local;

import com.android.example.github.browser.user.data.model.User;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * Data Access Object for the users table.
 */
@Dao
public interface UsersDao {
    @Query("SELECT * FROM users ORDER BY local_created_at DESC")
    Flowable<List<User>> getAllUsers();

    @Query("SELECT * FROM users WHERE user_name = :userName LIMIT 1")
    Flowable<User> getUser(String userName);

    @Query("SELECT * FROM users WHERE id = :id")
    Flowable<User> getUserById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(User user);

    @Query("DELETE FROM users WHERE id = :id")
    Completable deleteUser(long id);

    @Query("DELETE FROM users")
    Maybe<Integer> deleteUsers();
}
