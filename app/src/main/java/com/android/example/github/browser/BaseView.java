package com.android.example.github.browser;

import androidx.annotation.StringRes;

public interface BaseView<T> {
    void showError(Throwable t);
    void showMessage(@StringRes int resId);
    boolean isActive();
}
