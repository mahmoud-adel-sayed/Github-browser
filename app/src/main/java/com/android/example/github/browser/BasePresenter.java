package com.android.example.github.browser;

public interface BasePresenter<T> {
    void subscribe(T view);
    void unsubscribe();
}
