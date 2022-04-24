package com.example.fineance.controller.mainActivity;

import static com.example.fineance.model.PerformNetworkRequest.createTransaction;
import static com.example.fineance.model.PerformNetworkRequest.getDepenses;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.fineance.R;
import com.example.fineance.controller.categoryActivity.CategorieFragment;
import com.example.fineance.model.Depense;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNav;


    @SuppressLint("NonConstantResourceId")
    ArrayList<Depense> depenseArrayList;
    Boolean isOnPrevision=false;
    String savedPrevision;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Gets Permissions
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            this.requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        depenseArrayList = new ArrayList<>();
        getDepenses();
        bottomNav = this.findViewById(R.id.bot_nav_bar);
        this.getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new HomeFragment()).commit();
        bottomNav.setSelectedItemId(R.id.nav_home);
        bottomNav.setOnItemSelectedListener(item -> {

            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.nav_home:
                    isOnPrevision=false;
                    fragment = new HomeFragment();
                    break;
                case R.id.nav_categories:
                    isOnPrevision=false;
                    fragment = new CategorieFragment();
                    break;
                case R.id.nav_prevision:
                    isOnPrevision=true;
                    fragment = new PrevisionFragment();
                    break;
            }
            if (fragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();
            }
            return true;
        });
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        Fragment fragment;
        isOnPrevision = savedInstanceState.getBoolean(savedPrevision);
        if (isOnPrevision) {
            fragment = new PrevisionFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();
        }
        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putBoolean(savedPrevision, isOnPrevision);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        Fragment fragment;
        if (intent != null) {
            Depense depense = intent.getParcelableExtra("depense");
            if (depense != null) {
                createTransaction(depense);
            }
        }
        if (isOnPrevision) {
            fragment = new PrevisionFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();
        }

    }



}