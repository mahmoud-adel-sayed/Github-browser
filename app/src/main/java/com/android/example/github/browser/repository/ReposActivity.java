package com.android.example.github.browser.repository;

import android.os.Bundle;
import android.view.MenuItem;

import com.android.example.github.browser.R;

import javax.inject.Inject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import dagger.Lazy;
import dagger.android.support.DaggerAppCompatActivity;

import static com.android.example.github.browser.util.ActivityUtil.addFragmentToActivity;

public class ReposActivity extends DaggerAppCompatActivity {

    @Inject Lazy<ReposFragment> mFragmentProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repos);
        // Set up the toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(R.string.repos_label);

        ReposFragment fragment =
                (ReposFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (fragment == null) {
            fragment = mFragmentProvider.get();
            addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.content_frame);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
