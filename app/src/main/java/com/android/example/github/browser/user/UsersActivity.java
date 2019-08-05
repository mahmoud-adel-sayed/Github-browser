package com.android.example.github.browser.user;

import android.os.Bundle;

import com.android.example.github.browser.R;

import javax.inject.Inject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import dagger.Lazy;
import dagger.android.support.DaggerAppCompatActivity;

import static com.android.example.github.browser.util.ActivityUtil.addFragmentToActivity;

public class UsersActivity extends DaggerAppCompatActivity {

    @Inject Lazy<UsersFragment> mFragmentProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        // Set up the toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(false);
        ab.setTitle(R.string.users_label);

        UsersFragment fragment =
                (UsersFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (fragment == null) {
            fragment = mFragmentProvider.get();
            addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.content_frame);
        }
    }
}
