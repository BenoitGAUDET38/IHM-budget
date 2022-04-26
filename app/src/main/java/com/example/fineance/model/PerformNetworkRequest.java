package com.example.fineance.model;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

public class PerformNetworkRequest extends AsyncTask<Void, Void, String> {

    public static final int CODE_GET_REQUEST = 1024;
    public static final int CODE_POST_REQUEST = 1025;

    private static final String ROOT_URL = "https://fineance.000webhostapp.com/FineAnceApi/v1/Api.php?apicall=";

    public static final String URL_CREATE_DEPENSE = ROOT_URL + "createtransaction";
    public static final String URL_GET_TRANSACTIONS = ROOT_URL + "gettransactions";
    public static final String URL_UPDATE_TRANSACTION = ROOT_URL + "updatetransaction";
    public static final String URL_DELETE_TRANSACTION = ROOT_URL + "deletetransaction&id=";
    public static ArrayList<Depense> depenseList = new ArrayList<>();
    //the url where we need to send the request
    String url;
    //the parameters
    HashMap<String, String> params;
    //the request code to define whether it is a GET or POST
    int requestCode;

    //constructor to initialize values
    public PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
        this.url = url;
        this.params = params;
        this.requestCode = requestCode;
    }

    private static void readDepenses() {
        PerformNetworkRequest request = new PerformNetworkRequest(URL_GET_TRANSACTIONS, null, CODE_GET_REQUEST);
        request.execute();
    }

    private static ArrayList<Depense> refreshDepenseList(JSONArray depenseArray) throws JSONException {
        ArrayList<Depense> depenses = new ArrayList<>();

        //traversing through all the items in the json array
        //the json we got from the response
        for (int i = 0; i < depenseArray.length(); i++) {
            //getting each hero object
            JSONObject obj = depenseArray.getJSONObject(i);
            //adding the hero to the list
            depenses.add(new Depense(
                    obj.getInt("id"),
                    obj.getString("nom"),
                    obj.getInt("categorie"),
                    obj.getString("provenance"),
                    obj.getDouble("montant"),
                    obj.getString("devise"),
                    obj.getString("commentaire"),
                    Timestamp.valueOf(obj.getString("date"))
            ));
        }
        return depenses;
    }

    public static void updateTransaction(int id, String nom, double montant, String devise, String categorie, String commentaire, String provenance) {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        params.put("nom", nom);
        params.put("montant", String.valueOf(montant));
        params.put("devise", devise);
        params.put("categorie", categorie);
        params.put("commentaire", commentaire);
        params.put("provenance", provenance);
        PerformNetworkRequest request = new PerformNetworkRequest(URL_UPDATE_TRANSACTION, params, CODE_POST_REQUEST);
        request.execute();
    }

    public static void deleteTransaction(int id) {
        PerformNetworkRequest request = new PerformNetworkRequest(URL_DELETE_TRANSACTION + id, null, CODE_GET_REQUEST);
        request.execute();
    }

    public static void createTransaction(String nom, int categorie, String provenance, double montant, String devise, String commentaire) {
        HashMap<String, String> params = new HashMap<>();
        params.put("nom", nom);
        params.put("categorie", String.valueOf(categorie));
        params.put("provenance", provenance);
        params.put("montant", String.valueOf(montant));
        params.put("devise", devise);
        params.put("commentaire", commentaire);
        //Calling the create hero API
        PerformNetworkRequest request = new PerformNetworkRequest(URL_CREATE_DEPENSE, params, CODE_POST_REQUEST);
        request.execute();
    }

    public static void createTransaction(Depense d) {
        createTransaction(d.getNom(), d.getCategorie(), d.getProvenance(), d.getMontant(), d.getDevise(), d.getCommentaire());
    }

    public static ArrayList<Depense> getDepenses() {
        readDepenses();
//        while(depenseList.equals(new ArrayList<>()));
        return depenseList;
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
        Log.d("DEBUG", "onPostExecuteTry");
        try {
            JSONObject object = new JSONObject(s);
            if (!object.getBoolean("error")) {
                //TODO Reporter la notif plus haut
//                Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                //refreshing the herolist after every operation
                Log.d("DEBUG", "depenseConstruction");
                depenseList = refreshDepenseList(object.getJSONArray("transactions"));
            }
            Log.d("DEBUG", "onPostExecute: " + depenseList);
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
