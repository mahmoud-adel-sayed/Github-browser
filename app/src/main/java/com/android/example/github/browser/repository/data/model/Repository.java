package com.android.example.github.browser.repository.data.model;

import com.android.example.github.browser.user.data.model.User;
import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Model class for a Github Repository.
 */
@Entity(tableName = "repositories",
        indices = @Index(value = "user_id"),
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "user_id")
)
public final class Repository {

    // Server database id
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private final long mId;

    @NonNull
    @ColumnInfo(name = "name")
    @SerializedName("name")
    private final String mName;

    @Nullable
    @ColumnInfo(name = "full_name")
    @SerializedName("full_name")
    private final String mFullName;

    @Nullable
    @ColumnInfo(name = "description")
    @SerializedName("description")
    private final String mDescription;

    @Nullable
    @ColumnInfo(name = "language")
    @SerializedName("language")
    private final String mLanguage;

    @ColumnInfo(name = "user_id")
    private long mUserId;

    // Local date not server date
    @ColumnInfo(name = "local_created_at")
    private long mCreatedAt;

    public Repository(long id, @NonNull String name, @Nullable String fullName,
                      @Nullable String description, @Nullable String language) {
        mId = id;
        mName = name;
        mFullName = fullName;
        mDescription = description;
        mLanguage = language;
    }

    public long getId() {
        return mId;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    @Nullable
    public String getFullName() {
        return mFullName;
    }

    @Nullable
    public String getDescription() {
        return mDescription;
    }

    @Nullable
    public String getLanguage() {
        return mLanguage;
    }

    public long getUserId() {
        return mUserId;
    }

    public void setUserId(long userId) {
        mUserId = userId;
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
        Repository repository = (Repository) o;
        return Objects.equal(mId, repository.mId) &&
               Objects.equal(mName, repository.mName) &&
               Objects.equal(mFullName, repository.mFullName) &&
               Objects.equal(mDescription, repository.mDescription) &&
               Objects.equal(mLanguage, repository.mLanguage);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mName, mFullName, mDescription, mLanguage);
    }

    @Override
    public String toString() {
        return "{ id:" + mId +
                ", name:" + mName +
                ", full_name:" + mFullName +
                ", description:" + mDescription +
                ", language:" + mLanguage +
                ", user_id:" + mUserId +
                ", local_created_at: " + mCreatedAt + " }";
    }
}
