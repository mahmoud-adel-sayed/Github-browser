package com.android.example.github.browser.repository.data.source.remote;

import com.android.example.github.browser.repository.data.model.Repository;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReposApiInterface {
    @GET("/users/{userName}/repos")
    Flowable<List<Repository>> getRepos(@Path("userName") String userName,
                                        @Query("sort") String sort,
                                        @Query("direction") String direction,
                                        @Query("per_page") int perPage,
                                        @Query("page") int page);
}
