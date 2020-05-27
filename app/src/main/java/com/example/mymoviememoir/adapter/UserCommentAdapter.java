package com.example.mymoviememoir.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mymoviememoir.R;
import com.example.mymoviememoir.network.reponse.StatusesItem;
import com.example.mymoviememoir.utils.Values;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author sunkai
 */
public class UserCommentAdapter extends RecyclerView.Adapter<UserCommentAdapter.CommentViewHolder> {
    private List<StatusesItem> comments;


    public UserCommentAdapter(List<StatusesItem> comments) {
        this.comments = comments;
    }


    static class CommentViewHolder extends RecyclerView.ViewHolder {
        private TextView comment;
        private RoundedImageView userImage;
        private TextView userName;
        private TextView postDate;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View view) {
            comment = view.findViewById(R.id.comment);
            userImage = view.findViewById(R.id.user_image);
            userName = view.findViewById(R.id.user_name);
            postDate = view.findViewById(R.id.post_date);
        }
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_comment_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss Z yyyy", Locale.getDefault());
        StatusesItem item = comments.get(position);
        try {
            Date createDate = simpleDateFormat.parse(item.getCreatedAt());
            holder.postDate.setText(Values.SIMPLE_DATE_FORMAT.format(createDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Glide.with(holder.itemView).load(item.getUser().getProfileImageUrlHttps()).into(holder.userImage);
        holder.userName.setText(item.getUser().getName());

        holder.comment.setText(item.getText());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}
