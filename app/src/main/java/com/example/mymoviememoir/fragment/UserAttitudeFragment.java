package com.example.mymoviememoir.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviememoir.R;
import com.example.mymoviememoir.adapter.UserCommentAdapter;
import com.example.mymoviememoir.network.reponse.StatusesItem;

import java.util.ArrayList;
import java.util.List;

public class UserAttitudeFragment extends Fragment {
    public static final String COMMENTS = "comments";

    public static UserAttitudeFragment newInstance(ArrayList<StatusesItem> comments) {

        Bundle args = new Bundle();
        args.putParcelableArrayList(COMMENTS, comments);
        UserAttitudeFragment fragment = new UserAttitudeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private ArrayList<StatusesItem> comments;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_attitude_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        comments = getArguments().getParcelableArrayList(COMMENTS);
        initView(view);
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        UserCommentAdapter adapter = new UserCommentAdapter(comments);
        recyclerView.setAdapter(adapter);
    }
}
