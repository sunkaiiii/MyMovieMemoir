package com.example.mymoviememoir.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mymoviememoir.R;
import com.example.mymoviememoir.fragment.models.MainTopViewPersonModel;
import com.example.mymoviememoir.network.request.SignUpPersonRequest;
import com.example.mymoviememoir.utils.PersonInfoUtils;
import com.example.mymoviememoir.utils.Values;

import java.util.Calendar;
import java.util.Locale;

public class MainTopViewFragment extends Fragment {
    private MainTopViewPersonModel mViewModel;
    private LinearLayout llTopWelcomeView;
    private TextView welcomeUserName;
    private TextView currentDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_top_layout,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        llTopWelcomeView = view.findViewById(R.id.ll_top_welcome_view);
        welcomeUserName = view.findViewById(R.id.welcome_user_name);
        currentDate = view.findViewById(R.id.current_date);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(MainTopViewPersonModel.class);
        mViewModel.getPerson().observe(getViewLifecycleOwner(), this::fillPersonInformation);
        readPersonInformation();
    }

    private void readPersonInformation() {
        mViewModel.setPerson(PersonInfoUtils.getPersonInstance());
    }

    private void fillPersonInformation(SignUpPersonRequest signUpPersonRequest) {
        llTopWelcomeView.setVisibility(View.VISIBLE);
        welcomeUserName.setText(String.format("Welcome, %s", signUpPersonRequest.getFname()));
        currentDate.setText(Values.MAIN_FRAGMENT_DISPLAY_TIME_FORMAT.format(Calendar.getInstance(Locale.getDefault()).getTime()));
    }
}
