package com.example.fineance.model;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PerformNetworkRequest extends AsyncTask<Void, Void, String> {

    public static final int CODE_GET_REQUEST = 1024;
    public static final int CODE_POST_REQUEST = 1025;

    private static final String ROOT_URL = "http://192.168.182.40/FineAnceApi/v1/Api.php?apicall=";

    public static final String URL_CREATE_HERO = ROOT_URL + "createtransaction";
    public static final String URL_READ_HEROES = ROOT_URL + "gettransactions";
    public static final String URL_UPDATE_HERO = ROOT_URL + "updatetransaction";
    public static final String URL_DELETE_HERO = ROOT_URL + "deletetransaction&id=";


    //the url where we need to send the request
    String url;

    //the parameters
    HashMap<String, String> params;

    //the request code to define whether it is a GET or POST
    int requestCode;

    public static ArrayList<Depense> depenseList = new ArrayList<>();

    //constructor to initialize values
    public PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
        this.url = url;
        this.params = params;
        this.requestCode = requestCode;
    }

    //when the task started displaying a progressbar
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d("DEBUG", "onPreExecute: ");
    }


    //this method will give the response from the request
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject object = new JSONObject(s);
            if (!object.getBoolean("error")) {
                //TODO Reporter la notif plus haut
//                Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                //refreshing the herolist after every operation
                depenseList = refreshDepenseList(object.getJSONArray("transactions"));
            }
            Log.d("DEBUG", "onPostExecute: "+depenseList);
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

    private static void readDepenses() {
        PerformNetworkRequest request = new PerformNetworkRequest(URL_READ_HEROES, null, CODE_GET_REQUEST);
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
                    obj.getString("categorie")," ",
//                    obj.getString("provenance"),
                    obj.getDouble("montant"),
                    obj.getString("devise"),
                    obj.getString("commentaire")
            ));
        }
        return depenses;
    }

    public static void updateTransaction(int id,String name,String realname,int rating,String team) {
        HashMap<String, String> params = new HashMap<>();
        params.put("id",String.valueOf(id));
        params.put("nom", name);
        params.put("devise", "EUR");
        params.put("categorie", realname);
        params.put("montant", String.valueOf(rating));
        params.put("commentaire", team);
        PerformNetworkRequest request = new PerformNetworkRequest(URL_UPDATE_HERO, params, CODE_POST_REQUEST);
        request.execute();
    }

    public static void deleteTransaction(int id) {
        PerformNetworkRequest request = new PerformNetworkRequest(URL_DELETE_HERO + id, null, CODE_GET_REQUEST);
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
        PerformNetworkRequest request = new PerformNetworkRequest(URL_CREATE_HERO, params, CODE_POST_REQUEST);
        request.execute();
    }

    public static ArrayList<Depense> getDepenses(){
        readDepenses();
//        while(depenseList.equals(new ArrayList<>()));
        return depenseList;
    }
}
