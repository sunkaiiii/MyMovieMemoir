package com.example.mymoviememoir.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviememoir.R;
import com.example.mymoviememoir.network.reponse.CrewItem;

import java.util.List;

/**
 * @author sunkai
 */
public class MovieCrewAdapter extends RecyclerView.Adapter<MovieCrewAdapter.CrewHolder> {
    private List<CrewItem> crewItems;

    public MovieCrewAdapter(List<CrewItem> creItems) {
        this.crewItems = creItems;
    }

    static class CrewHolder extends RecyclerView.ViewHolder {

        public TextView crewName;
        public TextView crewJob;

        public CrewHolder(@NonNull View itemView) {
            super(itemView);
            crewName = itemView.findViewById(R.id.crew_name);
            crewJob = itemView.findViewById(R.id.crew_job);
        }
    }

    @NonNull
    @Override
    public CrewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CrewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.crew_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CrewHolder holder, int position) {
        CrewItem crewItem = crewItems.get(position);
        holder.crewName.setText(crewItem.getName());
        holder.crewJob.setText(crewItem.getJob());
    }

    @Override
    public int getItemCount() {
        return crewItems.size();
    }
}
