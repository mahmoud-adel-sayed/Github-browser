package com.android.example.github.browser.repository;

import com.android.example.github.browser.R;
import com.android.example.github.browser.UseCaseHandler;
import com.android.example.github.browser.repository.data.model.Repository;
import com.android.example.github.browser.repository.domain.usecase.GetRepos;
import com.android.example.github.browser.repository.domain.usecase.SaveRepo;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Listens to user actions from the UI ({@link ReposFragment}), retrieves the data and updates the
 * UI as required.
 */
class ReposPresenter implements ReposContract.Presenter {

    private ReposContract.View mView;
    private final UseCaseHandler mUseCaseHandler;
    private final GetRepos mGetRepos;
    private final SaveRepo mSaveRepo;
    private final CompositeDisposable mCompositeDisposable;

    @Inject
    ReposPresenter(UseCaseHandler useCaseHandler, GetRepos getRepos, SaveRepo saveRepo) {
        mUseCaseHandler = useCaseHandler;
        mGetRepos = getRepos;
        mSaveRepo = saveRepo;
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getRepos(long userId, @NonNull String userName, int page) {
        GetRepos.RequestValues requestValues = new GetRepos.RequestValues(userId, userName, page);
        Disposable disposable = mUseCaseHandler.execute(mGetRepos, requestValues)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mView.setLoadingIndicator(false))
                .subscribe(
                        // onNext
                        repos -> {
                            mView.setListLoadingIndicator(false);
                            mView.showRepos(repos);
                        },
                        // onError
                        throwable -> {
                            mView.setListLoadingIndicator(false);
                            mView.showError(throwable);
                        }
                );
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void openRepoDetails(@NonNull Repository repository) {
        mView.showRepoDetails(repository.getId());
    }

    @Override
    public void saveRepo(@NonNull Repository repository) {
        SaveRepo.RequestValues requestValues = new SaveRepo.RequestValues(repository);
        Disposable disposable = mUseCaseHandler.execute(mSaveRepo, requestValues)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> mView.showMessage(R.string.mess_repo_created),
                        throwable -> mView.showError(throwable)
                );
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void clearRepos() {

    }

    @Override
    public void subscribe(ReposContract.View view) {
        mView = view;
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
        mView = null;
    }
}
