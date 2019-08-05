package com.android.example.github.browser.user.data.source.remote;

import com.android.example.github.browser.user.data.model.User;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UsersApiInterface {
    @GET("/users/{userName}")
    Flowable<User> getUser(@Path("userName") String userName);
}
