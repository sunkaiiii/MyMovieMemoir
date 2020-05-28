package com.example.mymoviememoir.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviememoir.R;
import com.example.mymoviememoir.adapter.WatchListAdapter;
import com.example.mymoviememoir.room.model.WatchListViewModel;

/**
 * @author sunkai
 */
public class WatchListFragment extends BaseRequestRestfulServiceFragment {

    WatchListViewModel viewModel;
    private RecyclerView watchList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_watch_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(WatchListViewModel.class);
        viewModel.initalizeVars(getActivity().getApplication());
        initView(view);
        loadWatchList();
    }

    private void initView(View view) {
        watchList = view.findViewById(R.id.watch_list);
    }

    private void loadWatchList() {
        viewModel.getAllWatchList().observe(getViewLifecycleOwner(), (watchLists -> {
            WatchListAdapter adapter = new WatchListAdapter(watchLists, viewModel);
            watchList.setAdapter(adapter);
        }));
    }


}
