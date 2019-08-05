package com.android.example.github.browser.user;

import com.android.example.github.browser.di.scope.ActivityScoped;
import com.android.example.github.browser.di.scope.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * {@link UsersPresenter}.
 */
@Module
public abstract class UsersModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract UsersFragment usersFragment();

    @ActivityScoped
    @Binds
    abstract UsersContract.Presenter usersPresenter(UsersPresenter presenter);
}
