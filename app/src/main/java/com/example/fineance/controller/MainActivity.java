package com.example.fineance.controller;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.fineance.R;
import com.example.fineance.model.Depense;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;
import com.google.android.material.navigation.NavigationBarView;

import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNav;

    @SuppressLint("NonConstantResourceId")
    ArrayList<Depense> depenseArrayList;
    ImageView moneyCircleButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Gets Permissions
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            this.requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
        }

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        bottomNav = this.findViewById(R.id.bot_nav_bar);
        this.getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new HomeFragment()).commit();
        bottomNav.setSelectedItemId(R.id.nav_home);
        bottomNav.setOnItemSelectedListener(item -> {

            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.nav_home:
                    fragment = new HomeFragment();
                    break;
                case R.id.nav_categories:
                    fragment = new CategorieFragment();
                    break;
                case R.id.nav_prevision:
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
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (intent != null){
            Depense depense = intent.getParcelableExtra("depense");
            if (depense != null){
                depenseArrayList.add(depense);
            }
        }
    }
}