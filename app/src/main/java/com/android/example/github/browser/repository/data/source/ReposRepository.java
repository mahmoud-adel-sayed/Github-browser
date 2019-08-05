package com.android.example.github.browser.repository.data.source;

import android.util.Log;

import com.android.example.github.browser.MemoryCache;
import com.android.example.github.browser.di.qualifier.Local;
import com.android.example.github.browser.di.qualifier.Remote;
import com.android.example.github.browser.repository.data.model.Repository;
import com.android.example.github.browser.repository.data.model.UserRepo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation to load repos from the data sources into a cache.
 */
@Singleton
public class ReposRepository implements ReposDataSource {
    private static final String TAG = ReposRepository.class.getSimpleName();

    private final ReposDataSource mRemoteReposDataSource;
    private final ReposDataSource mLocalReposDataSource;
    private final MemoryCache mMemCache;
    private boolean mCacheIsDirty = false;

    @Inject
    public ReposRepository(@Remote ReposDataSource remoteReposDataSource,
                           @Local ReposDataSource localReposDataSource,
                           MemoryCache memoryCache) {
        mRemoteReposDataSource = remoteReposDataSource;
        mLocalReposDataSource = localReposDataSource;
        mMemCache = memoryCache;
    }

    @Override
    public Flowable<List<UserRepo>> getAllRepos() {
        return mLocalReposDataSource.getAllRepos();
    }

    @Override
    public Flowable<List<Repository>> getRepos(long userId, @NonNull String userName, int page) {
        checkNotNull(userName);
        // Respond immediately with cache if available and not dirty
        List<Repository> cache = mMemCache.get(getCacheKey(userId, page));
        if ((cache != null && !cache.isEmpty()) && !mCacheIsDirty) {
            Log.e(TAG, "got from memory cache");
            return Flowable.fromIterable(cache).toList().toFlowable();
        }

        Flowable<List<Repository>> remoteRepos = getAndSaveRemoteRepos(userId, userName, page);
        if (mCacheIsDirty) {
            return remoteRepos;
        }
        else {
            // Query the local storage if available. If not, query the network.
            Flowable<List<Repository>> localRepos = getAndCacheLocalRepos(userId, userName, page);
            return localRepos.take(1)
                    .filter(repos -> !repos.isEmpty())
                    .switchIfEmpty(remoteRepos);
        }
    }

    private Flowable<List<Repository>> getAndCacheLocalRepos(long userId, String userName, int page) {
        return mLocalReposDataSource.getRepos(userId, userName, page)
                .flatMap(repos -> Flowable.fromIterable(repos)
                        .toList()
                        .doAfterSuccess(list -> {
                            mMemCache.add(getCacheKey(userId, page), list);
                            Log.e(TAG, "put in memory cache from local: " + list.toString());
                        })
                        .toFlowable());
    }

    private Flowable<List<Repository>> getAndSaveRemoteRepos(long userId, String userName, int page) {
        return mRemoteReposDataSource.getRepos(userId, userName, page)
                .flatMap(repos -> Flowable.fromIterable(repos)
                        .doOnNext(repo -> {
                            repo.setUserId(userId);
                            repo.setCreatedAt(System.currentTimeMillis());
                            mLocalReposDataSource.save(repo);
                        })
                        .toList()
                        .doAfterSuccess(list -> {
                            mMemCache.add(getCacheKey(userId, page), list);
                            Log.e(TAG, "put in memory cache from remote: " + list.toString());
                        })
                        .toFlowable()
                )
                .doOnComplete(() -> mCacheIsDirty = false);
    }

    private static String getCacheKey(long userId, int page) {
        return "REPOS" + "-" + userId + "-" + page;
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
        checkNotNull(repository);
        return mLocalReposDataSource.saveRepo(repository);
    }

    @Override
    public Completable updateRepo(@NonNull Repository repository) {
        checkNotNull(repository);
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
