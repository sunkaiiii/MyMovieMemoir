package com.example.mymoviememoir.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mymoviememoir.R;
import com.example.mymoviememoir.network.RequestHost;
import com.example.mymoviememoir.network.reponse.CastItem;

import java.util.List;

public class MovieDetailCastAdapter extends RecyclerView.Adapter<MovieDetailCastAdapter.CastViewHolder> {
    private List<CastItem> castItems;

    public MovieDetailCastAdapter(List<CastItem> castItems) {
        this.castItems = castItems;
    }

    static class CastViewHolder extends RecyclerView.ViewHolder {

        public ImageView castImage;
        public TextView castName;
        public TextView castClassification;

        public CastViewHolder(@NonNull View itemView) {
            super(itemView);
            castImage = itemView.findViewById(R.id.cast_image);
            castName = itemView.findViewById(R.id.cast_name);
            castClassification = itemView.findViewById(R.id.cast_classification);
        }
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CastViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_detail_cast_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {
        CastItem item = castItems.get(position);
        holder.castName.setText(item.getName());
        holder.castClassification.setText(item.getCharacter());
        Glide.with(holder.itemView).load(RequestHost.MOVIE_DB_IMAGE_HOST.getHostUrl() + item.getProfilePath()).into(holder.castImage);
    }

    @Override
    public int getItemCount() {
        return castItems.size();
    }
}
