package com.android.example.github.browser.user;

import com.android.example.github.browser.BasePresenter;
import com.android.example.github.browser.BaseView;
import com.android.example.github.browser.user.data.model.User;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface UsersContract {

    interface View extends BaseView<Presenter> {
        void showUsers(List<User> users);
        void addUser(User user);
        void showUserRepos(long userId, @NonNull String userName);
        void setFetchButtonEnabled(boolean enabled);
    }

    interface Presenter extends BasePresenter<View> {
        void getStoredUsers();
        void getUser(@NonNull String userName);
        void openUserRepos(@NonNull User user);
    }
}
