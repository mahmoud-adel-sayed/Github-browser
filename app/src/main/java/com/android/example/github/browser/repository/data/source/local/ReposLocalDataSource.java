package com.android.example.github.browser.repository.data.source.local;

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

import static com.android.example.github.browser.Config.PER_PAGE;

/**
 * Concrete implementation of a data source as a db.
 */
@Singleton
public class ReposLocalDataSource implements ReposDataSource {

    private final ReposDao mReposDao;

    @Inject
    public ReposLocalDataSource(ReposDao reposDao) {
        mReposDao = reposDao;
    }

    @Override
    public Flowable<List<UserRepo>> getAllRepos() {
        return mReposDao.getAllRepos();
    }

    @Override
    public Flowable<List<Repository>> getRepos(long userId, @NonNull String userName, int page) {
        int offset = (page-1) * PER_PAGE;
        return mReposDao.getRepos(userId, PER_PAGE, offset);
    }

    @Override
    public Flowable<Repository> getRepoById(long id) {
        return mReposDao.getRepoById(id);
    }

    @Override
    public long save(Repository repository) {
        return mReposDao.insert(repository);
    }

    @Override
    public Completable saveRepo(@NonNull Repository repository) {
        return mReposDao.insertRepo(repository);
    }

    @Override
    public Completable updateRepo(@NonNull Repository repository) {
        return mReposDao.updateRepo(repository);
    }

    @Override
    public Completable updateRepoName(long id, String name) {
        return mReposDao.updateRepoName(id, name);
    }

    @Override
    public Completable deleteRepo(long id) {
        return mReposDao.deleteRepo(id);
    }

    @Override
    public Maybe<Integer> deleteRepos() {
        return mReposDao.deleteRepos();
    }
}
