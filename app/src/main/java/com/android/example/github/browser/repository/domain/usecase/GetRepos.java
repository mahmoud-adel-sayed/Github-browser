package com.android.example.github.browser.repository.domain.usecase;

import com.android.example.github.browser.UseCase;
import com.android.example.github.browser.repository.data.model.Repository;
import com.android.example.github.browser.repository.data.source.ReposRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Fetches the list of Github repos for a specific user.
 */
public final class GetRepos extends UseCase<GetRepos.RequestValues, Flowable<List<Repository>>> {

    private final ReposRepository mRepository;

    @Inject
    public GetRepos(ReposRepository reposRepository) {
        mRepository = reposRepository;
    }

    @Override
    protected Flowable<List<Repository>> executeUseCase(RequestValues values) {
        return mRepository.getRepos(values.getUserId(), values.getUserName(), values.getPage());
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final long mUserId;
        private final String mUserName;
        private final int mPage;

        public RequestValues(long userId, String userName, int page) {
            mUserId = userId;
            mUserName = userName;
            mPage = page;
        }

        public long getUserId() {
            return mUserId;
        }

        public String getUserName() {
            return mUserName;
        }

        public int getPage() {
            return mPage;
        }
    }
}
