package com.android.example.github.browser.repository.data.model;

import androidx.room.ColumnInfo;

public class UserRepo {
    public String repoName;
    @ColumnInfo(name = "description") public String description;
    @ColumnInfo(name = "language") public String language;
    public String userName;
    public String userAvatarUrl;
}
