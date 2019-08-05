package com.android.example.github.browser.repository;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.example.github.browser.R;
import com.android.example.github.browser.di.scope.ActivityScoped;
import com.android.example.github.browser.repository.data.model.Repository;
import com.android.example.github.browser.util.scroll.EndlessRecyclerViewScrollListener;
import com.android.example.github.browser.util.ui.DividerLineDecoration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

import static com.google.common.base.Preconditions.checkNotNull;

@ActivityScoped
public class ReposFragment extends DaggerFragment implements ReposContract.View {
    private static final String TAG = ReposFragment.class.getSimpleName();

    @Inject ReposContract.Presenter mPresenter;
    @Inject long mUserId;
    @Inject String mUserName;

    @BindView(R.id.progress_bar) View mProgressBar;
    @BindView(R.id.tv_no_repos) TextView mErrorTV;
    private List<Repository> mRepos;
    private ReposAdapter mAdapter;
    private EndlessRecyclerViewScrollListener mEndlessScrollListener;

    // List's progress & retry item
    private static final Repository PROGRESS_ITEM = null;
    private static final Repository RETRY_ITEM =
            new Repository(-1, null, null, null, null);

    @Inject
    public ReposFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepos = new ArrayList<>(0);
        mAdapter = new ReposAdapter(mRepos, new ReposAdapter.OnRepoClickListener() {
            @Override
            public void onItemClick(Repository repository) {
                mPresenter.openRepoDetails(repository);
            }

            @Override
            public void onRetryClick() {
                retryLoadingRepos();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repos, container, false);
        ButterKnife.bind(this, view);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_repos);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(
                new DividerLineDecoration(getActivity(), R.drawable.recycler_line_divider));
        recyclerView.setAdapter(mAdapter);
        // Init endless listener and add it to the recyclerView
        mEndlessScrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.e(TAG, "Page index " + page);
                getRepos(page);
            }
        };
        recyclerView.addOnScrollListener(mEndlessScrollListener);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.subscribe(this);
        getRepos(1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    private void getRepos(int page) {
        // Show loadingView in the bottom of the list
        if (page != 1) setListLoadingIndicator(true);
        mPresenter.getRepos(mUserId, mUserName, page);
    }

    @Override
    public void setListLoadingIndicator(boolean active) {
        if (active) {
            // Add scrolling progress item
            mRepos.add(PROGRESS_ITEM);
            mAdapter.notifyItemInserted(mRepos.size());
        }
        else {
            // Remove scrolling progress bar
            if (!mRepos.isEmpty()) {
                mRepos.remove(mRepos.size() - 1);
                mAdapter.notifyItemRemoved(mRepos.size());
            }
        }
    }

    private void showNoInternetConnection() {
        if (mRepos.isEmpty()) {
            mErrorTV.setText(R.string.err_no_internet_connection);
        }
        else {
            // Add retry button
            mRepos.add(RETRY_ITEM);
            mAdapter.notifyItemInserted(mRepos.size());
        }
    }

    private void retryLoadingRepos() {
        // Remove retry button
        if (!mRepos.isEmpty()) {
            mRepos.remove(mRepos.size() - 1);
            mAdapter.notifyItemRemoved(mRepos.size());
        }
        // Try to get data again
        int page = mEndlessScrollListener.getCurrentPage() + 1;
        getRepos(page);
        mEndlessScrollListener.setLoading(true);
        Log.e(TAG, "Retry page index " + page);
    }

    @Override
    public void showRepos(List<Repository> repos) {
        // Show no repos message when there is no data available
        if (mRepos.isEmpty() && repos.isEmpty()) {
            mErrorTV.setText(R.string.no_repos_label);
        }
        else if (mRepos.isEmpty() && repos.size() == 1) {
            mRepos.addAll(repos);
            mAdapter.notifyDataSetChanged();
        }
        else {
            int curSize = mAdapter.getItemCount();
            mRepos.addAll(repos);
            mAdapter.notifyItemRangeInserted(curSize, mRepos.size() - 1);
        }
    }

    @Override
    public void showRepoDetails(long repositoryId) {

    }

    @Override
    public void showNoRepos() {

    }

    @Override
    public void setLoadingIndicator(boolean active) {
        mProgressBar.setVisibility(active ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError(Throwable t) {
        Log.e(TAG, t.getMessage());
        if (t instanceof IOException) {
            showNoInternetConnection();
        }
    }

    @Override
    public void showMessage(@StringRes int resId) {
        Toast.makeText(getActivity(), resId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    static class ReposAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int VIEW_ITEM = 0;
        private static final int VIEW_PROGRESS = 1;
        private static final int VIEW_RETRY = 2;

        private List<Repository> mRepos;
        private final OnRepoClickListener mListener;

        public interface OnRepoClickListener {
            void onItemClick(Repository repository);
            void onRetryClick();
        }

        ReposAdapter(List<Repository> repos, OnRepoClickListener listener) {
            mRepos = checkNotNull(repos);
            mListener = listener;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder vh;
            if (viewType == VIEW_PROGRESS) {
                vh = new ProgressViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.footer_progress, parent, false));
            }
            else if (viewType == VIEW_RETRY) {
                vh = new RetryViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.retry_request, parent, false));
            }
            else {
                vh = new ItemViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.repo_list_item, parent, false));
            }
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
            if (holder instanceof ItemViewHolder) {
                ItemViewHolder itemHolder = (ItemViewHolder) holder;

                Repository repository = mRepos.get(position);
                itemHolder.nameTV.setText(repository.getName());
                itemHolder.descriptionTV.setText(repository.getDescription());
                itemHolder.languageTV.setText(repository.getLanguage());
            }
            else if (holder instanceof ProgressViewHolder) {
                ProgressViewHolder progressHolder = (ProgressViewHolder) holder;
                progressHolder.progressBar.setIndeterminate(true);
            }
        }

        @Override
        public int getItemCount() {
            return mRepos.size();
        }

        @Override
        public int getItemViewType(int position) {
            Repository repository = mRepos.get(position);
            // Footer progress
            if (repository == null) return VIEW_PROGRESS;
            // Footer retry button
            if (repository.getId() == -1) return VIEW_RETRY;
            // order View type
            return VIEW_ITEM;
        }

        final class ItemViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_name) TextView nameTV;
            @BindView(R.id.tv_description) TextView descriptionTV;
            @BindView(R.id.tv_language) TextView languageTV;

            ItemViewHolder(View v) {
                super(v);
                ButterKnife.bind(this, v);
                v.setOnClickListener(view -> mListener.onItemClick(mRepos.get(getAdapterPosition())));
            }
        }

        // Define a view holder for Footer progress view
        final static class ProgressViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.footer_progress) ProgressBar progressBar;

            ProgressViewHolder(View v) {
                super(v);
                ButterKnife.bind(this, v);
            }
        }

        // Define a view holder for Footer retry button view
        private final class RetryViewHolder extends RecyclerView.ViewHolder {
            RetryViewHolder(View v) {
                super(v);
                v.findViewById(R.id.btn_retry).setOnClickListener(view -> mListener.onRetryClick());
            }
        }
    }
}
