package com.android.example.github.browser.di;

import com.android.example.github.browser.Config;
import com.android.example.github.browser.repository.data.source.remote.ReposApiInterface;
import com.android.example.github.browser.user.data.source.remote.UsersApiInterface;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
class NetModule {
    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .followRedirects(true)
                .followSslRedirects(true)
                .retryOnConnectionFailure(true)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder()
                .baseUrl(Config.BASE_SERVER_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    UsersApiInterface provideUsersApiInterface(Retrofit retrofit) {
        return retrofit.create(UsersApiInterface.class);
    }

    @Provides
    @Singleton
    ReposApiInterface provideReposApiInterface(Retrofit retrofit) {
        return retrofit.create(ReposApiInterface.class);
    }
}
