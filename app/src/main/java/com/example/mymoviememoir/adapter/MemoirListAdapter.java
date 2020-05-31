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
import com.example.mymoviememoir.entities.Memoir;
import com.example.mymoviememoir.utils.Values;
import com.example.mymoviememoir.view.RatingSelectView;

import java.util.List;

public class MemoirListAdapter extends RecyclerView.Adapter<MemoirListAdapter.MemoirHolder> {
    private List<Memoir> memoirs;
    private OnMemoirClickListener listener;

    public MemoirListAdapter(List<Memoir> memoirs) {
        this.memoirs = memoirs;
    }


    static class MemoirHolder extends RecyclerView.ViewHolder {
        public ImageView movieImage;
        public TextView movieName;
        public TextView movieReleaseDate;
        public RatingSelectView ratingScore;
        public TextView watchedDate;
        public TextView cinemaName;
        public TextView cinemaSuburb;
        public TextView userComment;
        public TextView publicRating;

        public MemoirHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View view) {
            movieImage = view.findViewById(R.id.movie_image);
            movieName = view.findViewById(R.id.movie_name);
            movieReleaseDate = view.findViewById(R.id.movie_release_date);
            ratingScore = view.findViewById(R.id.rating_score);
            watchedDate = view.findViewById(R.id.watched_date);
            cinemaName = view.findViewById(R.id.cinema_name);
            cinemaSuburb = view.findViewById(R.id.cinema_suburb);
            userComment = view.findViewById(R.id.user_comment);
            publicRating = view.findViewById(R.id.public_rating);
        }
    }

    @NonNull
    @Override
    public MemoirHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MemoirHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.memoir_list_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MemoirHolder holder, int position) {
        Memoir memoir = memoirs.get(position);
        holder.cinemaName.setText(memoir.getCinemaId().getCinemaName());
        holder.cinemaSuburb.setText(memoir.getCinemaId().getLocationSuburb());
        Glide.with(holder.itemView).load(memoir.getMovieImage()).into(holder.movieImage);
        holder.movieName.setText(memoir.getMovieName());
        try {
            holder.watchedDate.setText(Values.SIMPLE_DATE_FORMAT.format(Values.RESPONSE_FORMAT.parse(memoir.getWatchedDate())));
            holder.movieReleaseDate.setText(String.format("(%s)", Values.SIMPLE_DATE_FORMAT.format(Values.RESPONSE_FORMAT.parse(memoir.getMovieReleaseDate()))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.userComment.setText(memoir.getMemoirComment());
        holder.userComment.setOnClickListener((v) -> {
            if (holder.userComment.getMaxLines() == 3) {
                holder.userComment.setMaxLines(10000);
            } else {
                holder.userComment.setMaxLines(2);
            }
        });
        holder.ratingScore.setEnabled(false);
        holder.ratingScore.setRating((float) memoir.getRatingScore());
        holder.publicRating.setText(String.valueOf(memoir.getPublicRating()));
        /*
        To request startActivityForResult method
        Make a callback to the Fragment
         */
        holder.itemView.setOnClickListener((v) -> {
            if(listener!=null){
                listener.onItemClick(v,memoir);
            }
        });
    }

    @Override
    public int getItemCount() {
        return memoirs == null ? 0 : memoirs.size();
    }

    public interface OnMemoirClickListener{
        void onItemClick(View v, Memoir data);
    }

    public void setOnMemoirClickListener(OnMemoirClickListener listener) {
        this.listener = listener;
    }

    public void setNewData(List<Memoir> newData) {
        this.memoirs = newData;
        notifyDataSetChanged();
    }
}
