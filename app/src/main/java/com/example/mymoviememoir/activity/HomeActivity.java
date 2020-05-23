package com.example.mymoviememoir.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;

import com.example.mymoviememoir.R;
import com.example.mymoviememoir.fragment.HomeFragment;
import com.example.mymoviememoir.fragment.MainTopViewFragment;
import com.example.mymoviememoir.fragment.MemoirFragment;
import com.example.mymoviememoir.fragment.MovieSearchFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sunkai
 */
public class HomeActivity extends BaseRequestRestfulServiceActivity implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    private Map<Integer, Fragment> fragmentMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        HomeFragment homeFragment = new HomeFragment();
        MainTopViewFragment mainTopViewFragment = new MainTopViewFragment();
        fragmentMap = new HashMap<>();
        fragmentMap.put(R.layout.main_fragment, homeFragment);
        fragmentMap.put(R.layout.fragment_main_top_layout, mainTopViewFragment);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, homeFragment);
        fragmentTransaction.replace(R.id.top_frame_view, mainTopViewFragment);
        fragmentTransaction.commit();
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        drawerLayout.closeDrawer(GravityCompat.START);
        final Fragment fragment;
        MainTopViewFragment topViewFragment = (MainTopViewFragment) fragmentMap.get(R.layout.fragment_main_top_layout);
        if (topViewFragment == null) {
            topViewFragment = new MainTopViewFragment();
            fragmentMap.put(R.layout.fragment_main_top_layout, topViewFragment);
        }
        switch (item.getItemId()) {
            case R.id.home:
                fragmentTransaction.replace(R.id.top_frame_view, topViewFragment);
                fragment = fragmentMap.get(R.layout.main_fragment);
                break;
            case R.id.search:
                if (fragmentMap.containsKey(R.layout.fragment_movie_search)) {
                    fragment = fragmentMap.get(R.layout.fragment_movie_search);
                } else {
                    fragment = new MovieSearchFragment();
                    fragmentMap.put(R.layout.fragment_movie_search, fragment);
                }

                break;
            case R.id.memoir:
                if(fragmentMap.containsKey(R.layout.fragment_memoir)){
                    fragment = fragmentMap.get(R.layout.fragment_memoir);
                }else{
                    fragment = new MemoirFragment();
                    fragmentMap.put(R.layout.fragment_memoir,fragment);
                }

                break;
            case R.id.reports:
                fragment = null;
                break;
            case R.id.maps:
                fragment = null;
                break;
            default:
                fragment = null;
                break;
        }
        if(item.getItemId()!=R.id.home){
            fragmentTransaction.remove(topViewFragment);
        }
        if (fragment != null) {
            fragmentTransaction.replace(R.id.content_frame, fragment);
        }
        fragmentTransaction.commit();
        return true;
    }
}
