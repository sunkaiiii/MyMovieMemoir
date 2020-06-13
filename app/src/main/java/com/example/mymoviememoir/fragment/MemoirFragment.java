package com.example.mymoviememoir.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mymoviememoir.R;
import com.example.mymoviememoir.activity.AddMemoirActivity;
import com.example.mymoviememoir.activity.MovieDetailViewActivity;
import com.example.mymoviememoir.adapter.MemoirListAdapter;
import com.example.mymoviememoir.dialog.SelectYearDialog;
import com.example.mymoviememoir.entities.Memoir;
import com.example.mymoviememoir.entities.Person;
import com.example.mymoviememoir.fragment.models.MemoirListModel;
import com.example.mymoviememoir.network.MyMovieMemoirRestfulAPI;
import com.example.mymoviememoir.network.RequestHelper;
import com.example.mymoviememoir.network.interfaces.RestfulGetModel;
import com.example.mymoviememoir.utils.CredentialInfoUtils;
import com.example.mymoviememoir.utils.GsonUtils;
import com.example.mymoviememoir.utils.PersonInfoUtils;
import com.example.mymoviememoir.utils.Values;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sunkai
 */
public class MemoirFragment extends BaseRequestRestfulServiceFragment {


    private RecyclerView memoirList;
    private Spinner sortSpinner;
    private Spinner filterSpinner;
    private SwipeRefreshLayout swipeRefresh;

    private MemoirListAdapter adapter;
    private MemoirListModel memoirListModel;
    private View filterSearchLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        memoirListModel = new ViewModelProvider(this).get(MemoirListModel.class);
        return inflater.inflate(R.layout.fragment_memoir, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        requestHttp();
    }

    private void requestHttp() {
        final MyMovieMemoirRestfulAPI api;
        final String[] sortOptions = getResources().getStringArray(R.array.sort_options);
        final String selectItem = sortSpinner.getSelectedItem().toString();
        if (TextUtils.equals(selectItem, sortOptions[0])) {
            api = MyMovieMemoirRestfulAPI.GET_MEMOIR_BY_ID;
        } else if (TextUtils.equals(selectItem, sortOptions[1])) {
            api = MyMovieMemoirRestfulAPI.GET_MEMOIR_BY_ID_ORBER_BY_RATING_SCORE;
        } else if (TextUtils.equals(selectItem, sortOptions[2])) {
            api = MyMovieMemoirRestfulAPI.GET_MEMOIR_BY_ID_ORDER_BY_PUBLIC_SCORE;
        } else {
            api = MyMovieMemoirRestfulAPI.GET_MEMOIR_BY_ID;
        }
        requestRestfulService(api, (RestfulGetModel) () -> Collections.singletonList(String.valueOf(PersonInfoUtils.getPersonInstance().orElse(new Person()).getId())));
    }

    @Override
    public void onPostExecute(RequestHelper helper, String response) {
        super.onPostExecute(helper, response);
        swipeRefresh.setRefreshing(false);
        try {
            switch (helper.getRestfulAPI()) {
                case GET_MEMOIR_BY_ID:
                case GET_MEMOIR_BY_ID_ORBER_BY_RATING_SCORE:
                case GET_MEMOIR_BY_ID_ORDER_BY_PUBLIC_SCORE:
                    List<Memoir> memoirs = GsonUtils.fromJsonToList(response, Memoir.class);
                    memoirListModel.setMemoirs(memoirs);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void preExecute(RequestHelper helper) {
        super.preExecute(helper);
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void onExecuteFailed(RequestHelper helper, String message, Exception ex) {
        super.onExecuteFailed(helper, message, ex);
        swipeRefresh.setRefreshing(false);
    }

    private void setDataIntoListView(List<Memoir> memoirs) {
        if (filterSearchLayout.getVisibility() == View.GONE) {
            final Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_right_in);
            filterSearchLayout.setVisibility(View.VISIBLE);
            filterSearchLayout.startAnimation(animation);
        }
        final List<Memoir> filterData = filterData(memoirs);
        adapter.setNewData(filterData);
    }

    private List<Memoir> filterData(List<Memoir> memoirs) {
        final List<Memoir> filterData = new ArrayList<>();
        final String[] filters = getResources().getStringArray(R.array.filter_options);
        final String selectItem = filterSpinner.getSelectedItem().toString();
        if (TextUtils.equals(filters[0], selectItem)) {
            filterData.addAll(memoirs);
        } else if (TextUtils.equals(filters[1], selectItem)) {
            filterData.addAll(getMemoirOfAYear(memoirs, Calendar.getInstance().get(Calendar.YEAR)));
        } else if (TextUtils.equals(filters[2], selectItem)) {
            new SelectYearDialog().setOnYearConfirmListener((year) -> {
                filterData.addAll(getMemoirOfAYear(memoirs, year));
                adapter.setNewData(filterData);
            }).setCancelListener(() -> {
                if (filterData.isEmpty()) {
                    filterData.addAll(memoirs);
                    adapter.setNewData(filterData);
                }
            }).show(getParentFragmentManager(), "SelectYearDialog");
        } else if (TextUtils.equals(filters[3], selectItem)) {
            for (Memoir memoir : memoirs) {
                if (memoir.getRatingScore() >= 4) {
                    filterData.add(memoir);
                }
            }
        } else if (TextUtils.equals(filters[4], selectItem)) {
            for (Memoir memoir : memoirs) {
                if (memoir.getRatingScore() <= 2) {
                    filterData.add(memoir);
                }
            }
        }
        return filterData;
    }

    private List<Memoir> getMemoirOfAYear(List<Memoir> memoirs, int year) {
        try {
            return memoirs.stream().filter(memoir -> LocalDate.parse(memoir.getWatchedDate(), Values.REQUESTING_FORMAT).getYear() == year).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void initView(View view) {
        memoirList = view.findViewById(R.id.memoir_list);
        filterSearchLayout = view.findViewById(R.id.filter_search_layout);
        sortSpinner = view.findViewById(R.id.sort_spinner);
        filterSpinner = view.findViewById(R.id.filter_spinner);
        swipeRefresh = view.findViewById(R.id.swipe_refresh);
        adapter = new MemoirListAdapter(memoirListModel.getMemoir().getValue());
        adapter.setOnMemoirClickListener(this::navigateToDetailView);
        memoirList.setAdapter(adapter);
        swipeRefresh.setOnRefreshListener(this::requestHttp);
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                requestHttp();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setDataIntoListView(memoirListModel.getMemoir().getValue());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        memoirListModel.getMemoir().observe(getViewLifecycleOwner(), this::setDataIntoListView);

        memoirList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                final Animation animation;
                if (dy > 0) {
                    if (filterSearchLayout.getVisibility() != View.GONE) {
                        animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_right_out);
                        filterSearchLayout.setVisibility(View.GONE);
                        filterSearchLayout.startAnimation(animation);
                    }
                } else {
                    if (filterSearchLayout.getVisibility() != View.VISIBLE) {
                        animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_right_in);
                        filterSearchLayout.setVisibility(View.VISIBLE);
                        filterSearchLayout.startAnimation(animation);
                    }
                }

            }
        });
    }

    private void navigateToDetailView(View view, Memoir memoir) {
        final Intent intent = new Intent(getContext(), MovieDetailViewActivity.class);
        intent.putExtra(MovieDetailViewActivity.ID, memoir.getMovieId());
        intent.putExtra(MovieDetailViewActivity.MOVIE_NAME, memoir.getMovieName());
        intent.putExtra(MovieDetailViewActivity.RELEASE_DATE, memoir.getMovieReleaseDate());
        startActivityForResult(intent, AddMemoirActivity.ADD_MEMOIR);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case AddMemoirActivity
                    .ADD_MEMOIR:
                if (resultCode == AddMemoirActivity.ADD_SUCCESS) {
                    requestHttp();
                }
                break;
            default:
                break;
        }
    }
}
