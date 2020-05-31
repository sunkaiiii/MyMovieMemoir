package com.example.mymoviememoir.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mymoviememoir.fragment.UserAttitudeFragment;
import com.example.mymoviememoir.network.reponse.StatusesItem;
import com.example.mymoviememoir.utils.BagOfWordsUtils;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author sunkai
 */
public class UserAttitudeViewPagerAdapter extends FragmentStateAdapter {
    private Map<BagOfWordsUtils.Classification, ArrayList<StatusesItem>> attitudeStrings;

    public UserAttitudeViewPagerAdapter(@NonNull FragmentManager manager, Lifecycle lifecycle, Map<BagOfWordsUtils.Classification, ArrayList<StatusesItem>> attitudeStrings) {
        super(manager, lifecycle);
        this.attitudeStrings = attitudeStrings;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return UserAttitudeFragment.newInstance(attitudeStrings.get(BagOfWordsUtils.Classification.values()[position]));
    }

    @Override
    public int getItemCount() {
        return attitudeStrings.size();
    }
}
