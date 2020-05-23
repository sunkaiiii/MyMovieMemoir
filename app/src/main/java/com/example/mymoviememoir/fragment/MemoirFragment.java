package com.example.mymoviememoir.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviememoir.R;
import com.example.mymoviememoir.adapter.MemoirListAdapter;
import com.example.mymoviememoir.entities.Memoir;
import com.example.mymoviememoir.network.MyMovieMemoirRestfulAPI;
import com.example.mymoviememoir.network.RequestHelper;
import com.example.mymoviememoir.network.RestfulGetModel;
import com.example.mymoviememoir.utils.CredentialInfoUtils;
import com.example.mymoviememoir.utils.GsonUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemoirFragment extends BaseRequestRestfulServiceFragment {


    private RecyclerView memoirList;
    private FrameLayout frameLayout;
    private Spinner sortSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_memoir, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        requestRestfulService(MyMovieMemoirRestfulAPI.GET_MEMOIR_BY_ID, (RestfulGetModel) () -> Collections.singletonList(String.valueOf(CredentialInfoUtils.getId())));
    }

    @Override
    public void onPostExecute(RequestHelper helper, String response) {
        super.onPostExecute(helper, response);
        try {
            switch (helper.getRestfulAPI()) {
                case GET_MEMOIR_BY_ID:
                    List<Memoir> memoirs = GsonUtils.fromJsonToList(response, Memoir.class);
                    MemoirListAdapter adapter = new MemoirListAdapter(memoirs);
                    memoirList.setAdapter(adapter);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView(View view) {
        memoirList = view.findViewById(R.id.memoir_list);
        frameLayout = view.findViewById(R.id.frameLayout);
        sortSpinner = view.findViewById(R.id.sort_spinner);
    }

}
