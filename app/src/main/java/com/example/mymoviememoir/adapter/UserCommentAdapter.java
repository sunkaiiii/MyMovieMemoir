package com.example.mymoviememoir.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.transition.Transition;
import com.example.mymoviememoir.R;

import java.util.List;

public class UserCommentAdapter extends RecyclerView.Adapter<UserCommentAdapter.CommentViewHolder> {
    private List<String> comments;

    public UserCommentAdapter(List<String> comments) {
        this.comments = comments;
    }


    static class CommentViewHolder extends RecyclerView.ViewHolder {
        private TextView comment;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            comment = itemView.findViewById(R.id.comment);
        }
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_comment_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        String comment = comments.get(position);
        holder.comment.setText(comment);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}
