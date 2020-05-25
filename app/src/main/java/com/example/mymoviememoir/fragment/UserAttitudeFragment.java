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

import java.util.ArrayList;
import java.util.List;

public class UserAttitudeFragment extends Fragment {
    public static final String COMMENTS = "comments";

    public static UserAttitudeFragment newInstance(ArrayList<String> comments) {

        Bundle args = new Bundle();
        args.putStringArrayList(COMMENTS, comments);
        UserAttitudeFragment fragment = new UserAttitudeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private List<String> comments;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_attitude_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        comments = getArguments().getStringArrayList(COMMENTS);
        initView(view);
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        UserCommentAdapter adapter = new UserCommentAdapter(comments);
        recyclerView.setAdapter(adapter);
    }
}
