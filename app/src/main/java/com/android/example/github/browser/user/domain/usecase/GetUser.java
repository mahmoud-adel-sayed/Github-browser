package com.android.example.github.browser.user.domain.usecase;

import com.android.example.github.browser.UseCase;
import com.android.example.github.browser.user.data.model.User;
import com.android.example.github.browser.user.data.source.UsersRepository;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Gets information about an Github user.
 */
public final class GetUser extends UseCase<GetUser.RequestValues, Flowable<User>> {

    private final UsersRepository mRepository;

    @Inject
    public GetUser(UsersRepository usersRepository) {
        mRepository = usersRepository;
    }

    @Override
    protected Flowable<User> executeUseCase(RequestValues values) {
        return mRepository.getUser(values.getUserName());
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final String mUserName;

        public RequestValues(String userName) {
            mUserName = userName;
        }

        public String getUserName() {
            return mUserName;
        }
    }
}
