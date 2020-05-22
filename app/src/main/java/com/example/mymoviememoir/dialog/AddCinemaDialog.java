package com.example.mymoviememoir.dialog;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mymoviememoir.R;
import com.example.mymoviememoir.network.DefaultRequestHttpAction;
import com.example.mymoviememoir.network.MyMovieMemoirRestfulAPI;
import com.example.mymoviememoir.network.OkHttpNetworkConnection;
import com.example.mymoviememoir.network.RequestHelper;
import com.example.mymoviememoir.network.request.AddCinemaRequest;
import com.example.mymoviememoir.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

/**
 * @author sunkai
 */
public class AddCinemaDialog extends DialogFragment implements DefaultRequestHttpAction {
    private TextInputEditText cinemaName;
    private TextInputEditText cinemaLocation;
    private Button btnCancel;
    private Button btnAdd;

    private OnAddCinemaSuccessListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_cinema_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView(view);
    }

    private void initView(View view) {
        cinemaName = view.findViewById(R.id.cinema_name);
        cinemaLocation = view.findViewById(R.id.cinema_location);
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnAdd = view.findViewById(R.id.btn_add);
        btnCancel.setOnClickListener((v) -> dismiss());
        btnAdd.setOnClickListener(this::tryToAddCinema);
    }

    private void tryToAddCinema(View v) {
        final Editable name = cinemaName.getText();
        final Editable location = cinemaLocation.getText();
        if (Utils.isBlank(name)) {
            cinemaName.setError("Cinema name cannot be empty");
            return;
        }
        if (Utils.isBlank(location)) {
            cinemaLocation.setError("Cinema location cannot be empty");
            return;
        }
        requestRestfulService(MyMovieMemoirRestfulAPI.ADD_CINEMA, new AddCinemaRequest(name.toString(), location.toString()));
    }

    @Override
    public void preExecute(RequestHelper helper) {
    }

    @Override
    public void onExecuteFailed(RequestHelper helper, String message, Exception ex) {
        if(ex instanceof OkHttpNetworkConnection.HTTPConnectionErrorException){
            if(((OkHttpNetworkConnection.HTTPConnectionErrorException) ex).getBody().contains("duplicate key")){
                Toast.makeText(getContext(), "The information of the cinema has been already existed, please input again", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onPostExecute(RequestHelper helper, String response) {
        switch (helper.getRestfulAPI()) {
            case ADD_CINEMA:
                if (listener != null) {
                    try {
                        listener.onSuccess((AddCinemaRequest) helper.getBodyRequestModel());
                        dismiss();
                    } catch (RequestHelper.NoSuchTypeOfModelException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    public void setOnAddCinemaSuccessListener(OnAddCinemaSuccessListener listener) {
        this.listener = listener;
    }


    public interface OnAddCinemaSuccessListener {
        void onSuccess(AddCinemaRequest request);
    }
}
