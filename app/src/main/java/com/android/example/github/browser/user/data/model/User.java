package com.android.example.github.browser.user.data.model;

import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Immutable model class for an Github user.
 */
@Entity(tableName = "users", indices = {@Index(value = "user_name")})
public final class User {

    // Server database id
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private final long mId;

    @NonNull
    @ColumnInfo(name = "user_name")
    @SerializedName("login")
    private final String mUserName;

    @NonNull
    @ColumnInfo(name = "name")
    @SerializedName("name")
    private final String mName;

    @Nullable
    @ColumnInfo(name = "avatar_url")
    @SerializedName("avatar_url")
    private final String mAvatarUrl;

    @NonNull
    @ColumnInfo(name = "html_url")
    @SerializedName("html_url")
    private final String mHtmlUrl;

    @NonNull
    @ColumnInfo(name = "type")
    @SerializedName("type")
    private final String mType;

    // Local date not server date
    @ColumnInfo(name = "local_created_at")
    private long mCreatedAt;

    public User(long id, @NonNull String userName, @NonNull String name,
                @Nullable String avatarUrl, @NonNull String htmlUrl, @NonNull String type) {
        mId = id;
        mUserName = userName;
        mName = name;
        mAvatarUrl = avatarUrl;
        mHtmlUrl = htmlUrl;
        mType = type;
    }

    public long getId() {
        return mId;
    }

    @NonNull
    public String getUserName() {
        return mUserName;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    @Nullable
    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    @NonNull
    public String getHtmlUrl() {
        return mHtmlUrl;
    }

    @NonNull
    public String getType() {
        return mType;
    }

    public long getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(long createdAt) {
        mCreatedAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equal(mId, user.mId) &&
               Objects.equal(mUserName, user.mUserName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mUserName);
    }

    @Override
    public String toString() {
        return "{ id:" + mId +
                ", user_name:" + mUserName +
                ", name:" + mName +
                ", avatar_url:" + mAvatarUrl +
                ", html_url:" + mHtmlUrl +
                ", type:" + mType +
                ", local_created_at: " + mCreatedAt + " }";
    }

}
