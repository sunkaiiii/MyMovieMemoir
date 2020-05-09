package com.example.mymoviememoir.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mymoviememoir.R;
import com.example.mymoviememoir.network.request.SignUpPersonRequest;
import com.example.mymoviememoir.utils.Values;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author sunkai
 */
public class MainFragment extends Fragment {

    private MainViewPersonModel mViewModel;
    private LinearLayout llTopWelcomeView;
    private TextView welcomeUserName;
    private TextView currentDate;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewPersonModel.class);
        mViewModel.getPerson().observe(getViewLifecycleOwner(), this::fillPersonInformation);
        readPersonInformation();
    }

    private void readPersonInformation() {
        final Activity activity = getActivity();
        if (activity == null) {
            return;
        }
        final String personJson = getActivity().getSharedPreferences(Values.USER_INFO, Context.MODE_PRIVATE).getString(Values.PERSON, "");
        if (!TextUtils.isEmpty(personJson)) {
            try {
                final SignUpPersonRequest person = new Gson().fromJson(personJson, SignUpPersonRequest.class);
                mViewModel.setPerson(person);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void fillPersonInformation(SignUpPersonRequest signUpPersonRequest) {
        llTopWelcomeView.setVisibility(View.VISIBLE);
        welcomeUserName.setText(String.format("Welcome, %s", signUpPersonRequest.getFname()));
        currentDate.setText(Values.MAIN_FRAGMENT_DISPLAY_TIME_FORMAT.format(Calendar.getInstance(Locale.getDefault()).getTime()));
    }

    private void initView(View view) {
        llTopWelcomeView = view.findViewById(R.id.ll_top_welcome_view);
        welcomeUserName = view.findViewById(R.id.welcome_user_name);
        currentDate = view.findViewById(R.id.current_date);
    }
}
