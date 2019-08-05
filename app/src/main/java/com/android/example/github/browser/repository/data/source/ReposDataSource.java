package com.android.example.github.browser.repository.data.source;

import com.android.example.github.browser.repository.data.model.Repository;
import com.android.example.github.browser.repository.data.model.UserRepo;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * Main entry point for accessing repos data.
 */
public interface ReposDataSource {
    Flowable<List<UserRepo>> getAllRepos();
    Flowable<List<Repository>> getRepos(long userId, @NonNull String userName, int page);
    Flowable<Repository> getRepoById(long id);
    long save(@NonNull Repository repository);
    Completable saveRepo(@NonNull Repository repository);
    Completable updateRepo(@NonNull Repository repository);
    Completable updateRepoName(long id, String name);
    Completable deleteRepo(long id);
    Maybe<Integer> deleteRepos();
}
