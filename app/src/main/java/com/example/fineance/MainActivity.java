package com.example.fineance;

import static com.example.fineance.modeles.PerformNetworkRequest.categoriesObservable;
import static com.example.fineance.modeles.PerformNetworkRequest.depensesObservable;
import static com.example.fineance.modeles.PerformNetworkRequest.getCategories;
import static com.example.fineance.modeles.PerformNetworkRequest.getDepenses;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.fineance.modeles.Depense;
import com.example.fineance.modeles.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {
    private static final String PREVISION_FRAGMENT_TAG = "prevision";
    private String isOn = "home";
    private String savedIsOn;
    private final CategorieFragment categorie = new CategorieFragment();
    private PrevisionFragment previsions = new PrevisionFragment();
    private final HomeFragment home = new HomeFragment();
    private User user = new User();

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        depensesObservable.addObserver(this);
        categoriesObservable.addObserver(this);
        getDepenses();
        getCategories();
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        this.setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = this.findViewById(R.id.bot_nav_bar);
        this.getSupportFragmentManager().beginTransaction().replace(R.id.main_container, home).commit();
        bottomNav.setSelectedItemId(R.id.nav_home);

        if (savedInstanceState != null) {
            previsions = (PrevisionFragment)
                    getSupportFragmentManager().findFragmentByTag(PREVISION_FRAGMENT_TAG);
        }


        bottomNav.setOnItemSelectedListener(item -> {

            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.nav_home:
                    isOn = "home";
                    fragment = home;
                    break;
                case R.id.nav_categories:
                    isOn = "categories";
                    fragment = categorie;
                    break;
                case R.id.nav_prevision:
                    isOn = "prevision";
                    if(previsions == null)
                        previsions = new PrevisionFragment();
                    fragment = previsions;
                    if (!fragment.isInLayout()) {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_container, fragment, PREVISION_FRAGMENT_TAG)
                                .commit();
                    }
                    System.out.println(previsions.getMoisActuel());
                    break;
            }
            if (fragment != null && !(fragment instanceof PrevisionFragment)) {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();
            }
            return true;
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putString(savedIsOn, isOn);
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);
        isOn = savedInstanceState.getString(savedIsOn);
        if (isOn.equals("prevision")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, previsions, PREVISION_FRAGMENT_TAG).commit();
        } else if (isOn.equals("categories")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, categorie).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MVC", "Test");
        home.userUpdate();
    }

    @Override
    public void update(Observable observable, Object o) {
        try {
            categorie.updateList((List) o);
            List<Depense> depenseArrayList = (List<Depense>) o;
            home.updateTotal(depenseArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}