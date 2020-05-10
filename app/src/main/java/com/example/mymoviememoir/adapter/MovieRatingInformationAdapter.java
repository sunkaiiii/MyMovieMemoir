package com.example.mymoviememoir.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviememoir.R;
import com.example.mymoviememoir.network.reponse.MovieRatingResponse;
import com.example.mymoviememoir.utils.Values;

import java.text.SimpleDateFormat;
import java.util.List;

public class MovieRatingInformationAdapter extends RecyclerView.Adapter<MovieRatingInformationAdapter.ViewHolder> {

    private List<MovieRatingResponse> mMovies;

    public MovieRatingInformationAdapter(List<MovieRatingResponse> movies) {
        this.mMovies = movies;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView movieName;
        TextView movieReleaseDate;
        TextView ratingScore;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieName = itemView.findViewById(R.id.movie_name);
            movieReleaseDate = itemView.findViewById(R.id.movie_release_date);
            ratingScore = itemView.findViewById(R.id.movie_rating);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_rating_inforamtion_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieRatingResponse data = mMovies.get(position);
        holder.movieName.setText(data.getMovieName());
        holder.movieReleaseDate.setText(Values.MAIN_FRAGMENT_DISPLAY_TIME_FORMAT.format(data.getReleaseDate()));
        holder.ratingScore.setText(String.valueOf(data.getRatingScore()));

    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }
}
