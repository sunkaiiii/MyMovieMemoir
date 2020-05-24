package com.example.mymoviememoir.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mymoviememoir.R;
import com.example.mymoviememoir.adapter.MemoirListAdapter;
import com.example.mymoviememoir.entities.Memoir;
import com.example.mymoviememoir.fragment.models.MemoirListModel;
import com.example.mymoviememoir.network.MyMovieMemoirRestfulAPI;
import com.example.mymoviememoir.network.RequestHelper;
import com.example.mymoviememoir.network.RestfulGetModel;
import com.example.mymoviememoir.utils.CredentialInfoUtils;
import com.example.mymoviememoir.utils.GsonUtils;

import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemoirFragment extends BaseRequestRestfulServiceFragment {


    private RecyclerView memoirList;
    private FrameLayout frameLayout;
    private Spinner sortSpinner;
    private SwipeRefreshLayout swipeRefresh;

    private MemoirListAdapter adapter;
    private MemoirListModel memoirListModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        memoirListModel = new MemoirListModel();
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
        requestRestfulService(api, (RestfulGetModel) () -> Collections.singletonList(String.valueOf(CredentialInfoUtils.getId())));
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
        adapter.setNewData(memoirs);
    }

    private void initView(View view) {
        memoirList = view.findViewById(R.id.memoir_list);
        frameLayout = view.findViewById(R.id.frameLayout);
        sortSpinner = view.findViewById(R.id.sort_spinner);
        swipeRefresh = view.findViewById(R.id.swipe_refresh);
        adapter = new MemoirListAdapter(memoirListModel.getMemoir().getValue());
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

        memoirListModel.getMemoir().observe(getViewLifecycleOwner(), this::setDataIntoListView);

    }

}
