package com.example.mymoviememoir.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.mymoviememoir.R;
import com.example.mymoviememoir.fragment.HomeFragment;
import com.example.mymoviememoir.fragment.MainTopViewFragment;

/**
 * @author sunkai
 */
public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, new HomeFragment());
        fragmentTransaction.replace(R.id.top_frame_view,new MainTopViewFragment());
        fragmentTransaction.commit();
    }
}
