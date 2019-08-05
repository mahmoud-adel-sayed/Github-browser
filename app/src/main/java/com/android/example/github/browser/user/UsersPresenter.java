package com.android.example.github.browser.user;

import com.android.example.github.browser.UseCaseHandler;
import com.android.example.github.browser.user.data.model.User;
import com.android.example.github.browser.user.domain.usecase.GetUser;
import com.android.example.github.browser.user.domain.usecase.GetUsers;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Listens to user actions from the UI ({@link UsersFragment}), retrieves the data and updates the
 * UI as required.
 */
class UsersPresenter implements UsersContract.Presenter {

    private UsersContract.View mView;
    private final UseCaseHandler mUseCaseHandler;
    private final GetUsers mGetUsers;
    private final GetUser mGetUser;
    private final CompositeDisposable mCompositeDisposable;

    @Inject
    UsersPresenter(UseCaseHandler useCaseHandler, GetUsers getUsers, GetUser getUser) {
        mUseCaseHandler = useCaseHandler;
        mGetUsers = getUsers;
        mGetUser = getUser;
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getStoredUsers() {
        Disposable disposable = mUseCaseHandler.execute(mGetUsers, new GetUsers.RequestValues())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        // onNext
                        users -> mView.showUsers(users),
                        // onError
                        throwable -> mView.showError(throwable)
                );
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void getUser(String userName) {
        mView.setFetchButtonEnabled(false);
        Disposable disposable = mUseCaseHandler.execute(mGetUser, new GetUser.RequestValues(userName))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mView.setFetchButtonEnabled(true))
                .subscribe(
                        // onNext
                        user -> mView.addUser(user),
                        // onError
                        throwable -> mView.showError(throwable)
                );
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void openUserRepos(User user) {
        mView.showUserRepos(user.getId(), user.getUserName());
    }

    @Override
    public void subscribe(UsersContract.View view) {
        mView = view;
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
        mView = null;
    }
}
