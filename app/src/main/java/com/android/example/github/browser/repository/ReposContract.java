package com.android.example.github.browser.repository;

import com.android.example.github.browser.BasePresenter;
import com.android.example.github.browser.BaseView;
import com.android.example.github.browser.repository.data.model.Repository;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface ReposContract {

    interface View extends BaseView<Presenter> {
        void showRepos(List<Repository> repositories);
        void showRepoDetails(long repositoryId);
        void setLoadingIndicator(boolean active);
        void setListLoadingIndicator(boolean active);
        void showNoRepos();
    }

    interface Presenter extends BasePresenter<View> {
        void getRepos(long userId, @NonNull String userName, int page);
        void openRepoDetails(@NonNull Repository repository);
        void saveRepo(@NonNull Repository repository);
        void clearRepos();
    }
}
