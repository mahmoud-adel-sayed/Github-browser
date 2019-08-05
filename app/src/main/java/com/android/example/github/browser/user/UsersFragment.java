package com.android.example.github.browser.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.example.github.browser.R;
import com.android.example.github.browser.di.scope.ActivityScoped;
import com.android.example.github.browser.repository.ReposActivity;
import com.android.example.github.browser.user.data.model.User;
import com.android.example.github.browser.util.ui.DividerLineDecoration;
import com.bumptech.glide.Glide;

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
public class UsersFragment extends DaggerFragment implements UsersContract.View {
    public static final String KEY_USER_ID = "KEY_USER_ID";
    public static final String KEY_USER_NAME = "KEY_USER_NAME";

    @Inject UsersContract.Presenter mPresenter;

    @BindView(R.id.et_user_name) EditText mUserNameET;
    @BindView(R.id.btn_fetch) Button mFetchBTN;
    private UsersAdapter mAdapter;

    @Inject
    public UsersFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new UsersAdapter(getActivity(), new ArrayList<>(0), user ->
                mPresenter.openUserRepos(user));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        ButterKnife.bind(this, view);

        RecyclerView usersRV = (RecyclerView) view.findViewById(R.id.rv_users);
        usersRV.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        usersRV.addItemDecoration(
                new DividerLineDecoration(getActivity(), R.drawable.recycler_line_divider));
        usersRV.setAdapter(mAdapter);

        mFetchBTN.setOnClickListener(v -> getUser(mUserNameET));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.subscribe(this);
        mPresenter.getStoredUsers();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    private void getUser(EditText editText) {
        String userName = editText.getText().toString().trim();
        if (userName.isEmpty()) {
            editText.setError(getString(R.string.err_user_name_required));
        }
        else {
            mPresenter.getUser(userName);
        }
    }

    @Override
    public void showUsers(List<User> users) {
        mAdapter.replaceData(users);
    }

    @Override
    public void addUser(User user) {

    }

    @Override
    public void showUserRepos(long userId, @NonNull String userName) {
        Intent intent = new Intent(getActivity(), ReposActivity.class);
        intent.putExtra(KEY_USER_ID, userId);
        intent.putExtra(KEY_USER_NAME, userName);
        startActivity(intent);
    }

    @Override
    public void setFetchButtonEnabled(boolean enabled) {
        if (enabled) {
            mFetchBTN.setEnabled(true);
            mFetchBTN.setAlpha(1.0f);
            mFetchBTN.setText(R.string.fetch_user_label);
        }
        else {
            mFetchBTN.setEnabled(false);
            mFetchBTN.setAlpha(0.3f);
            mFetchBTN.setText(R.string.loading_user_label);
        }
    }

    @Override
    public void showError(Throwable t) {
        Log.e("test", t.getMessage());
    }

    @Override
    public void showMessage(@StringRes int resId) {
        Toast.makeText(getActivity(), resId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    static class UsersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final Context mContext;
        private List<User> mUsers;
        private final OnUserClickListener mListener;

        public interface OnUserClickListener {
            void onItemClick(User user);
        }

        UsersAdapter(Context context, List<User> users, OnUserClickListener listener) {
            mContext = context;
            mUsers = checkNotNull(users);
            mListener = listener;
        }

        void replaceData(List<User> users) {
            mUsers = checkNotNull(users);
            notifyDataSetChanged();
        }

        void add(User user) {
            mUsers.add(user);
            notifyItemInserted(mUsers.size());
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.user_list_item, parent, false);
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
            ItemViewHolder itemHolder = (ItemViewHolder) holder;
            User user = mUsers.get(position);
            itemHolder.userNameTV.setText(user.getUserName());
            itemHolder.nameTV.setText(user.getName());
            Glide.with(mContext)
                    .load(user.getAvatarUrl())
                    .into(itemHolder.avatarIV);
        }

        @Override
        public int getItemCount() {
            return mUsers.size();
        }

        final class ItemViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_user_name) TextView userNameTV;
            @BindView(R.id.tv_name) TextView nameTV;
            @BindView(R.id.iv_avatar) ImageView avatarIV;

            ItemViewHolder(View v) {
                super(v);
                ButterKnife.bind(this, v);
                v.setOnClickListener(view -> mListener.onItemClick(mUsers.get(getAdapterPosition())));
            }
        }
    }
}
