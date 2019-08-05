package com.android.example.github.browser.repository.data.source.local;

import com.android.example.github.browser.repository.data.model.Repository;
import com.android.example.github.browser.repository.data.model.UserRepo;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * Data Access Object for the repositories table.
 */
@Dao
public interface ReposDao {

    @Query("SELECT repositories.name AS repoName, repositories.description, repositories.language, " +
            "users.user_name AS userName, users.avatar_url AS userAvatarUrl FROM repositories " +
            "INNER JOIN users ON repositories.user_id = users.id")
    Flowable<List<UserRepo>> getAllRepos();

    @Query("SELECT * FROM repositories WHERE user_id = :userId " +
            "ORDER BY local_created_at ASC LIMIT :perPage OFFSET :offset")
    Flowable<List<Repository>> getRepos(long userId, int perPage, int offset);

    @Query("SELECT * FROM repositories WHERE id = :id LIMIT 1")
    Flowable<Repository> getRepoById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Repository repository);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertRepo(Repository repository);

    @Update
    Completable updateRepo(Repository repository);

    @Query("UPDATE repositories SET name = :name WHERE id = :id")
    Completable updateRepoName(long id, String name);

    @Query("DELETE FROM repositories WHERE id = :id")
    Completable deleteRepo(long id);

    @Query("DELETE FROM repositories")
    Maybe<Integer> deleteRepos();
}
