package com.example.mymoviememoir.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mymoviememoir.R;
import com.example.mymoviememoir.fragment.HomeFragment;
import com.example.mymoviememoir.fragment.MapsFragment;
import com.example.mymoviememoir.fragment.MemoirFragment;
import com.example.mymoviememoir.fragment.MovieSearchFragment;
import com.example.mymoviememoir.fragment.ReportFragment;
import com.example.mymoviememoir.fragment.WatchListFragment;
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
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private Handler existHandler = new Handler(Looper.getMainLooper());
    private static final int EXIT =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
    }

    private void initViews() {
        HomeFragment homeFragment = new HomeFragment();
        fragmentMap = new HashMap<>();
        fragmentMap.put(R.layout.main_fragment, homeFragment);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, homeFragment);
        fragmentTransaction.commit();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigation_view);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        drawerLayout.closeDrawer(GravityCompat.START);
        final Fragment fragment;
        switch (item.getItemId()) {
            case R.id.home:
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
                if (fragmentMap.containsKey(R.layout.fragment_memoir)) {
                    fragment = fragmentMap.get(R.layout.fragment_memoir);
                } else {
                    fragment = new MemoirFragment();
                    fragmentMap.put(R.layout.fragment_memoir, fragment);
                }
                break;
            case R.id.watch_list:
                if (fragmentMap.containsKey(R.layout.fragment_watch_list)) {
                    fragment = fragmentMap.get(R.layout.fragment_watch_list);
                } else {
                    fragment = new WatchListFragment();
                    fragmentMap.put(R.layout.fragment_watch_list, fragment);
                }
                break;
            case R.id.reports:
                if (fragmentMap.containsKey(R.layout.fragment_report)) {
                    fragment = fragmentMap.get(R.layout.fragment_report);
                } else {
                    fragment = new ReportFragment();
                    fragmentMap.put(R.layout.fragment_report, fragment);
                }
                break;
            case R.id.maps:
                if (fragmentMap.containsKey(R.layout.fragment_maps)) {
                    fragment = fragmentMap.get(R.layout.fragment_maps);
                } else {
                    fragment = new MapsFragment();
                    fragmentMap.put(R.layout.fragment_maps, fragment);
                }
                break;
            default:
                fragment = null;
                break;
        }
        if (fragment != null) {
            fragmentTransaction.replace(R.id.content_frame, fragment);
        }
        fragmentTransaction.commit();
        return true;
    }

    @Override
    public void onBackPressed() {
        final Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if(!(fragment instanceof HomeFragment)){
            final Fragment homeFragment = fragmentMap.get(R.layout.main_fragment);
            if(homeFragment==null){
                return;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,homeFragment).commit();
            return;
        }else if(!existHandler.hasMessages(EXIT)){
            Toast.makeText(this, "press back again to exit the application", Toast.LENGTH_SHORT).show();
            existHandler.sendEmptyMessageDelayed(EXIT,3000);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
