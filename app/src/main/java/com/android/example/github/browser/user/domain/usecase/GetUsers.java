package com.android.example.github.browser.user.domain.usecase;

import com.android.example.github.browser.UseCase;
import com.android.example.github.browser.user.data.model.User;
import com.android.example.github.browser.user.data.source.UsersRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Fetches the list of Github users.
 */
public final class GetUsers extends UseCase<GetUsers.RequestValues, Flowable<List<User>>> {

    private final UsersRepository mRepository;

    @Inject
    public GetUsers(UsersRepository usersRepository) {
        mRepository = usersRepository;
    }

    @Override
    protected Flowable<List<User>> executeUseCase(RequestValues values) {
        return mRepository.getAllUsers();
    }

    public static final class RequestValues implements UseCase.RequestValues { }
}
