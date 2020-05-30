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
import com.example.mymoviememoir.network.reponse.MovieRatingResponse;
import com.example.mymoviememoir.utils.Values;
import com.example.mymoviememoir.view.RatingSelectView;

import java.util.List;

/**
 * @author sunkai
 */
public class MovieTopRatingInformationAdapter extends RecyclerView.Adapter<MovieTopRatingInformationAdapter.ViewHolder> {

    private List<MovieRatingResponse> mMovies;

    public MovieTopRatingInformationAdapter(List<MovieRatingResponse> movies) {
        this.mMovies = movies;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView movieName;
        TextView movieReleaseDate;
        RatingSelectView ratingScore;
        ImageView movieImage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieName = itemView.findViewById(R.id.movie_name);
            movieReleaseDate = itemView.findViewById(R.id.movie_release_date);
            ratingScore = itemView.findViewById(R.id.movie_rating);
            movieImage = itemView.findViewById(R.id.movie_image);
            ratingScore.setEnabled(false);
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
        holder.ratingScore.setRating(data.getRatingScore());
        Glide.with(holder.itemView).load(data.getMovieImage()).into(holder.movieImage);

    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }
}
