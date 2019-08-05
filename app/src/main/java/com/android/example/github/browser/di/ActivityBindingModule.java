package com.android.example.github.browser.di;

import com.android.example.github.browser.di.scope.ActivityScoped;
import com.android.example.github.browser.repository.ReposActivity;
import com.android.example.github.browser.repository.ReposModule;
import com.android.example.github.browser.user.UsersActivity;
import com.android.example.github.browser.user.UsersModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * We want Dagger.Android to create a SubComponent which has a parent Component of
 * whichever module ActivityBindingModule is on, in our case that will be AppComponent.
 * The beautiful part about this setup is that you never need to tell AppComponent that
 * it is going to have all these subComponents nor do you need to tell these subComponents that AppComponent exists.
 * We are also telling Dagger.Android that this generated SubComponent needs to
 * include the specified modules and be aware of a scope annotation @ActivityScoped
 * When Dagger.Android annotation processor runs it will create 4 subComponents for us.
 */
@Module
abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = UsersModule.class)
    abstract UsersActivity usersActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = ReposModule.class)
    abstract ReposActivity reposActivity();
}
