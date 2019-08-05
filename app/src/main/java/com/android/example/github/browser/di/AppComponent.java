package com.android.example.github.browser.di;

import android.app.Application;

import com.android.example.github.browser.App;
import com.android.example.github.browser.repository.data.source.ReposRepositoryModule;
import com.android.example.github.browser.user.data.source.UsersRepositoryModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * This is a Dagger component. Refer to {@link App} for the list of Dagger components
 * used in this application.
 * <p>
 * Even though Dagger allows annotating a {@link Component} as a singleton, the code
 * itself must ensure only one instance of the class is created. This is done in {@link App}.
 * {@link AndroidSupportInjectionModule} is the module from Dagger.Android that helps with
 * the generation and location of subComponents.
 */
@Singleton
@Component(modules = {
        AppModule.class,
        NetModule.class,
        DbModule.class,
        UsersRepositoryModule.class,
        ReposRepositoryModule.class,
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class
})
public interface AppComponent extends AndroidInjector<App> {

    // Gives us syntactic sugar. we can then do
    // DaggerAppComponent.builder().application(this).build().inject(this);
    // never having to instantiate any modules or say which module we are passing the application to.
    // Application will just be provided into our app graph now.
    @Component.Builder
    interface Builder {
        @BindsInstance AppComponent.Builder application(Application application);
        AppComponent build();
    }
}
