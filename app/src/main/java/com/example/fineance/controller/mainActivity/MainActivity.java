package com.example.fineance.controller.mainActivity;

import static com.example.fineance.model.PerformNetworkRequest.createTransaction;
import static com.example.fineance.model.PerformNetworkRequest.depensesObservable;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.fineance.R;
import com.example.fineance.controller.categoryActivity.CategorieFragment;
import com.example.fineance.model.Depense;
import com.example.fineance.model.DepenseUtilities;
import com.example.fineance.model.PerformNetworkRequest;
import com.example.fineance.model.notifications.Notification;
import com.example.fineance.model.notifications.notificationsFactories.AbstractNotificationFactory;
import com.example.fineance.model.notifications.notificationsFactories.HighPriorityNotificationFactory;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {
    BottomNavigationView bottomNav;


    @SuppressLint("NonConstantResourceId")
    List<Depense> depenseArrayList = new ArrayList<>();
    String isOn = "home";
    String savedIsOn;

    CategorieFragment categorie = new CategorieFragment();
    PrevisionFragment previsions = new PrevisionFragment();
    HomeFragment home = new HomeFragment();

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        depensesObservable.addObserver(this);
        PerformNetworkRequest.getDepenses();
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        this.setContentView(R.layout.activity_main);

        bottomNav = this.findViewById(R.id.bot_nav_bar);
        Log.d("DEBUG", String.valueOf(depenseArrayList));
        this.getSupportFragmentManager().beginTransaction().replace(R.id.main_container, home).commit();
        bottomNav.setSelectedItemId(R.id.nav_home);
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
                    fragment = previsions;
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
        super.onRestoreInstanceState(savedInstanceState);
        Fragment fragment;
        isOn = savedInstanceState.getString(savedIsOn);
        if (isOn.equals("prevision")) {
            fragment = new PrevisionFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();
        } else if (isOn.equals("categories")) {
//            fragment = categorie;
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, categorie).commit();
        }
    }

    private Fragment setCategorie() {
        categorie = new CategorieFragment();
        return categorie;
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
                createNotification(depense);
            }
        }
    }

    private void createNotification(Depense depense) {
        AbstractNotificationFactory factory = new HighPriorityNotificationFactory();
        Notification notif = factory.buildImageNotification(getApplicationContext(),
                getResources(),
                AbstractNotificationFactory.DEPENSE_IMG,
                "Nouvelle dépense", depense.getNom() + " d'une valeur de " +
                        depense.getMontant() + depense.getDevise() + " à " +
                        depense.getProvenance() + " a été ajouté !");
        notif.sendNotificationOnChannel();
    }

    @Override
    public void update(Observable observable, Object o) {
        Log.d("DEBUG","Recupere "+o);
        boolean res=true;
        try{
            depenseArrayList = (List<Depense>) o;
            Log.d("DEBUG","list now :"+depenseArrayList);
            home.updateTotal(DepenseUtilities.getMontantTotal(depenseArrayList));
            categorie.updateList(depenseArrayList);
        }catch (Exception e){
            res=false;
        }finally {
                Toast.makeText(getApplicationContext(),res?"Operation effectué avec succes":"Echec de l'operation",Toast.LENGTH_SHORT).show();
        }

    }
}