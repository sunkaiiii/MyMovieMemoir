package com.example.mymoviememoir.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mymoviememoir.fragment.UserAttitudeFragment;

import java.util.ArrayList;
import java.util.Map;

public class UserAttitudeViewPagerAdapter extends FragmentStateAdapter {
    private Map<String, ArrayList<String>> attitudeStrings;

    public UserAttitudeViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, Map<String, ArrayList<String>> attitudeStrings) {
        super(fragmentActivity);
        this.attitudeStrings = attitudeStrings;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return UserAttitudeFragment.newInstance(attitudeStrings.get(position));
    }

    @Override
    public int getItemCount() {
        return attitudeStrings.size();
    }
}
