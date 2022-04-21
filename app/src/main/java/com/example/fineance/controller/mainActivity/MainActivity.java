package com.example.fineance.controller.mainActivity;

import static com.example.fineance.model.Api.CODE_GET_REQUEST;
import static com.example.fineance.model.Api.CODE_POST_REQUEST;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.fineance.R;
import com.example.fineance.controller.CategorieFragment;
import com.example.fineance.model.Api;
import com.example.fineance.model.Depense;
import com.example.fineance.model.RequestHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNav;

    //we will use this list to display hero in listview
    List<Depense> heroList;

    //as the same button is used for create and update
    //we need to track whether it is an update or create operation
    //for this we have this boolean
    boolean isUpdating = false;


    @SuppressLint("NonConstantResourceId")
    ArrayList<Depense> depenseArrayList=new ArrayList<>();

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
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();

        if (intent != null) {
            Depense depense = intent.getParcelableExtra("depense");
            if (depense != null) {
                depenseArrayList.add(depense);
            }
        }

    }

    public static class PerformNetworkRequest extends AsyncTask<Void, Void, String> {

        //the url where we need to send the request
        String url;

        //the parameters
        HashMap<String, String> params;

        //the request code to define whether it is a GET or POST
        int requestCode;

        //constructor to initialize values
        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        //when the task started displaying a progressbar
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        //this method will give the response from the request
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
//                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    //refreshing the herolist after every operation
                    //so we get an updated list
                    //we will create this method right now it is commented
                    //because we haven't created it yet
                    refreshDepenseList(object.getJSONArray("transactions"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //the network operation will be performed in background
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);
            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);
            return null;
        }
    }

    private void readDepenses() {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_HEROES, null, CODE_GET_REQUEST);
        request.execute();
    }

    private static ArrayList<Depense> refreshDepenseList(JSONArray heroes) throws JSONException {
        ArrayList<Depense> depenses = new ArrayList<>();

        //traversing through all the items in the json array
        //the json we got from the response
        for (int i = 0; i < heroes.length(); i++) {
            //getting each hero object
            JSONObject obj = heroes.getJSONObject(i);

            //adding the hero to the list
            depenses.add(new Depense(
                    obj.getString("nom"),
                    obj.getString("categorie"),
                    obj.getString("provenance"),
                    obj.getDouble("montant"),
                    obj.getString("devise"),
                    obj.getString("commentaire")
            ));
        }
        return depenses;
    }

    private static void updateTransaction(int id,String name,String realname,int rating,String team) {
        HashMap<String, String> params = new HashMap<>();
        params.put("id",String.valueOf(id));
        params.put("nom", name);
        params.put("devise", "EUR");
        params.put("categorie", realname);
        params.put("montant", String.valueOf(rating));
        params.put("commentaire", team);
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_UPDATE_HERO, params, CODE_POST_REQUEST);
        request.execute();
    }

    private static void deleteTransaction(int id) {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_DELETE_HERO + id, null, CODE_GET_REQUEST);
        request.execute();
    }

    private static void createTransaction(String name,String realname,int rating,String team) {
        HashMap<String, String> params = new HashMap<>();
        params.put("nom", name);
        params.put("devise", "EUR");
        params.put("categorie", realname);
        params.put("montant", String.valueOf(rating));
        params.put("commentaire", team);

        //Calling the create hero API
        MainActivity.PerformNetworkRequest request = new MainActivity.PerformNetworkRequest(Api.URL_CREATE_HERO, params, CODE_POST_REQUEST);
        request.execute();
    }



}