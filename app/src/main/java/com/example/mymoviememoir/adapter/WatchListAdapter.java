package com.example.mymoviememoir.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.mymoviememoir.R;
import com.example.mymoviememoir.activity.MovieDetailViewActivity;
import com.example.mymoviememoir.room.entity.WatchList;
import com.example.mymoviememoir.room.model.WatchListViewModel;
import com.example.mymoviememoir.room.repository.WatchListRepository;
import com.example.mymoviememoir.utils.ColorUtils;

import java.util.List;

public class WatchListAdapter extends RecyclerView.Adapter<WatchListAdapter.WatchListViewHolder> {
    private List<WatchList> watchLists;
    private WatchListViewModel viewModel;

    public WatchListAdapter(List<WatchList> watchLists, WatchListViewModel viewModel) {
        this.watchLists = watchLists;
        this.viewModel = viewModel;
    }


    static class WatchListViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public ImageView imageView;
        public TextView movieName;
        public TextView releaseDate;
        public View buttonDelete;
        public TextView addedDate;
        public View contentLayout;

        public WatchListViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View view) {
            cardView = view.findViewById(R.id.cardView);
            imageView = view.findViewById(R.id.imageView);
            movieName = view.findViewById(R.id.movie_name);
            releaseDate = view.findViewById(R.id.release_date);
            buttonDelete = view.findViewById(R.id.button_delete);
            addedDate = view.findViewById(R.id.added_date);
            contentLayout = view.findViewById(R.id.content_layout);
        }
    }


    @NonNull
    @Override
    public WatchListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WatchListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.watch_list_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WatchListViewHolder holder, int position) {
        WatchList watchList = watchLists.get(position);
        holder.movieName.setText(watchList.getMovieName());
        holder.releaseDate.setText(String.format("Release: %s", watchList.getReleaseDate()));
        holder.addedDate.setText(String.format("Add On: %s", watchList.getAddedDateTime()));
        Glide.with(holder.itemView).load(watchList.getMovieImage()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                if (resource instanceof BitmapDrawable) {
                    holder.contentLayout.setBackgroundColor(ColorUtils.getDarkColor(((BitmapDrawable) resource).getBitmap()));
                }
                return false;
            }
        }).into(holder.imageView);
        holder.itemView.setOnClickListener((v) -> {
            final Intent intent = new Intent(v.getContext(), MovieDetailViewActivity.class);
            intent.putExtra(MovieDetailViewActivity.ID, watchList.getMovieId());
            intent.putExtra(MovieDetailViewActivity.RELEASE_DATE, watchList.getReleaseDate());
            intent.putExtra(MovieDetailViewActivity.MOVIE_NAME, watchList.getMovieName());
            v.getContext().startActivity(intent);
        });
        holder.buttonDelete.setOnClickListener((v) -> {
            showDeleteConfirmDialog(v.getContext(), watchList);
        });
    }

    private void showDeleteConfirmDialog(final Context context, final WatchList watchList) {
        new AlertDialog.Builder(context).setMessage("Delete this watch list?").setPositiveButton("Yes",
                (dialog, which) -> viewModel.delete(watchList))
                .setNegativeButton("No", null).create().show();
    }

    @Override
    public int getItemCount() {
        return watchLists == null ? 0 : watchLists.size();
    }
}
