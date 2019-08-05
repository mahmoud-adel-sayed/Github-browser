package com.android.example.github.browser.repository.data.source.remote;

import com.android.example.github.browser.repository.data.model.Repository;
import com.android.example.github.browser.repository.data.model.UserRepo;
import com.android.example.github.browser.repository.data.source.ReposDataSource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

import static com.android.example.github.browser.Config.DIRECTION_DESC;
import static com.android.example.github.browser.Config.PER_PAGE;
import static com.android.example.github.browser.Config.SORT_CREATED;

/**
 * Implementation of the data source as a server.
 */
@Singleton
public class ReposRemoteDataSource implements ReposDataSource {

    private final ReposApiInterface mApiInterface;

    @Inject
    public ReposRemoteDataSource(ReposApiInterface apiInterface) {
        mApiInterface = apiInterface;
    }

    @Override
    public Flowable<List<UserRepo>> getAllRepos() {
        return null;
    }

    @Override
    public Flowable<List<Repository>> getRepos(long userId, @NonNull String userName, int page) {
        return mApiInterface.getRepos(userName, SORT_CREATED, DIRECTION_DESC, PER_PAGE, page);
    }

    @Override
    public Flowable<Repository> getRepoById(long id) {
        return null;
    }

    @Override
    public long save(Repository repository) {
        return 0;
    }

    @Override
    public Completable saveRepo(@NonNull Repository repository) {
        return null;
    }

    @Override
    public Completable updateRepo(@NonNull Repository repository) {
        return null;
    }

    @Override
    public Completable updateRepoName(long id, String name) {
        return null;
    }

    @Override
    public Completable deleteRepo(long id) {
        return null;
    }

    @Override
    public Maybe<Integer> deleteRepos() {
        return null;
    }

}
