package com.example.fineance.controller.mainActivity;

import static com.example.fineance.model.PerformNetworkRequest.createTransaction;
import static com.example.fineance.model.PerformNetworkRequest.getDepenses;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.fineance.R;
import com.example.fineance.controller.categoryActivity.CategorieFragment;
import com.example.fineance.model.CalculDepense;
import com.example.fineance.model.Depense;
import com.example.fineance.model.notifications.Notification;
import com.example.fineance.model.notifications.notificationsFactories.AbstractNotificationFactory;
import com.example.fineance.model.notifications.notificationsFactories.HighPriorityNotificationFactory;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNav;


    @SuppressLint("NonConstantResourceId")
    ArrayList<Depense> depenseArrayList;
    String isOn;
    String savedIsOn;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
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
                    isOn = "home";
                    fragment = new HomeFragment();
                    Bundle args = new Bundle();
                    args.putDouble(HomeFragment.TOTAL_KEY, CalculDepense.getTotalDepenses(depenseArrayList));
                    args.putString(HomeFragment.DEVISE_KEY, "€"); // @TODO modifier plus tard en devise dynamique
                    fragment.setArguments(args);
                    break;
                case R.id.nav_categories:
                    isOn = "categories";
                    fragment = new CategorieFragment();
                    break;
                case R.id.nav_prevision:
                    isOn = "prevision";
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
        isOn = savedInstanceState.getString(savedIsOn);
        if (isOn.equals("prevision")) {
            fragment = new PrevisionFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();
        } else if (isOn.equals("categories")) {
            fragment = new CategorieFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();
        }
        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putString(savedIsOn, isOn);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (intent != null) {
            Depense depense = intent.getParcelableExtra("depense");
            if (depense != null && depense.valid()) {
                createTransaction(depense);
                AbstractNotificationFactory factory = new HighPriorityNotificationFactory();
                Notification notif = factory.buildImageNotification(getApplicationContext(),
                        getResources(),
                        AbstractNotificationFactory.DEPENSE_IMG,
                        "Nouvelle dépense", depense.getNom() + " d'une valeur de " +
                                depense.getMontant() + depense.getDevise() + " à " +
                                depense.getProvenance() + " a été ajouté !");
                notif.sendNotificationOnChannel();
            }
        }
    }
}