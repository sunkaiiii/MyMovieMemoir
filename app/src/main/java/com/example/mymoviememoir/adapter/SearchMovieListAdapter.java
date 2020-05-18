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
import com.example.mymoviememoir.network.reponse.MovieSearchListItem;

import java.util.List;

public class SearchMovieListAdapter extends RecyclerView.Adapter<SearchMovieListAdapter.SearchMovieViewHolder> {
    public List<MovieSearchListItem> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieSearchListItem> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    private List<MovieSearchListItem> movies;

    public SearchMovieListAdapter(List<MovieSearchListItem> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public SearchMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchMovieViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_movie_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchMovieViewHolder holder, int position) {
        MovieSearchListItem item = movies.get(position);
        holder.movieName.setText(item.getTitle());
        holder.movieReleaseDate.setText(item.getReleaseDate());
        holder.movieDesc.setText(item.getOverview());
        Glide.with(holder.itemView).load(RequestHost.MOVIE_DB_IMAGE_HOST.getHostUrl() + item.getPosterPath()).into(holder.movieImage);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class SearchMovieViewHolder extends RecyclerView.ViewHolder {

        public ImageView movieImage;
        public TextView movieName;
        public TextView movieReleaseDate;
        public TextView movieDesc;

        public SearchMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.movie_image);
            movieName = itemView.findViewById(R.id.movie_name);
            movieReleaseDate = itemView.findViewById(R.id.movie_release_date);
            movieDesc = itemView.findViewById(R.id.movie_desc);
        }
    }

}
