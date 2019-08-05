package com.android.example.github.browser.repository;

import com.android.example.github.browser.di.scope.ActivityScoped;
import com.android.example.github.browser.di.scope.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

import static com.android.example.github.browser.user.UsersFragment.KEY_USER_ID;
import static com.android.example.github.browser.user.UsersFragment.KEY_USER_NAME;

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * {@link ReposPresenter}.
 */
@Module
public abstract class ReposModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract ReposFragment reposFragment();

    @ActivityScoped
    @Binds
    abstract ReposContract.Presenter reposPresenter(ReposPresenter presenter);

    @ActivityScoped
    @Provides
    static long provideUserId(ReposActivity activity) {
        return activity.getIntent().getLongExtra(KEY_USER_ID, -1L);
    }

    @ActivityScoped
    @Provides
    static String provideUserName(ReposActivity activity) {
        return activity.getIntent().getStringExtra(KEY_USER_NAME);
    }
}
