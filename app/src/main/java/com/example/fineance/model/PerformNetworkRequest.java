package com.example.fineance.model;

import android.os.AsyncTask;
import android.util.Log;

import com.example.fineance.model.observables.CategorieObservable;
import com.example.fineance.model.observables.DepenseObservable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
    //TODO Decouper side-functions and execute
    public static final DepenseObservable depensesObservable = new DepenseObservable();
    public static final CategorieObservable categories = new CategorieObservable();

    public static final int CODE_GET_REQUEST = 1024;
    public static final int CODE_POST_REQUEST = 1025;

    private static final String ROOT_URL = "https://fineance.000webhostapp.com/FineAnceApi/v1/Api.php?apicall=";

    public static final String URL_CREATE_DEPENSE = ROOT_URL + "createtransaction";
    public static final String URL_GET_TRANSACTIONS = ROOT_URL + "gettransactions";
    public static final String URL_UPDATE_TRANSACTION = ROOT_URL + "updatetransaction";
    public static final String URL_DELETE_TRANSACTION = ROOT_URL + "deletetransaction&id=";
    public static final String URL_CREATE_CATEGORIE = ROOT_URL + "createcategorie";
    public static final String URL_GET_CATEGORIES = ROOT_URL + "getcategorie";
    public static final String URL_UPDATE_CATEGORIE = ROOT_URL + "updatecategorie";
    public static final String URL_DELETE_CATEGORIE = ROOT_URL + "deletecategorie&id=";
    public static List<Depense> depenseList = new ArrayList<>();
    public static List<Categorie> categoriesList = new ArrayList<>();
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

    private static void readCategories() {
        PerformNetworkRequest request = new PerformNetworkRequest(URL_GET_CATEGORIES, null, CODE_GET_REQUEST);
        request.execute();
    }

    private static List<Depense> refreshDepenseList(JSONArray depenseArray) throws JSONException {
        List<Depense> depenses = new ArrayList<>();

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

    private static List<Categorie> refreshCategorieList(JSONArray depenseArray) throws JSONException {
        ArrayList<Categorie> categories = new ArrayList<>();

        //traversing through all the items in the json array
        //the json we got from the response
        for (int i = 0; i < depenseArray.length(); i++) {
            //getting each hero object
            JSONObject obj = depenseArray.getJSONObject(i);
            //adding the hero to the list
            categories.add(new Categorie(
                    obj.getInt("id"),
                    obj.getString("nom"),
                    obj.getDouble("seuil"),
                    ""
            ));
        }
        return categories;
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

    public static void updateCategorie(int id, String nom, double seuil) {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        params.put("nom", nom);
        params.put("seuil", String.valueOf(seuil));
        PerformNetworkRequest request = new PerformNetworkRequest(URL_UPDATE_CATEGORIE, params, CODE_POST_REQUEST);
        request.execute();
    }

    public static void deleteTransaction(int id) {
        PerformNetworkRequest request = new PerformNetworkRequest(URL_DELETE_TRANSACTION + id, null, CODE_GET_REQUEST);
        request.execute();
    }

    public static void deleteCategorie(int id) {
        PerformNetworkRequest request = new PerformNetworkRequest(URL_DELETE_CATEGORIE + id, null, CODE_GET_REQUEST);
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
        PerformNetworkRequest request = new PerformNetworkRequest(URL_CREATE_DEPENSE, params, CODE_POST_REQUEST);
        request.execute();
    }

    public static void createTransaction(Depense d) {
        createTransaction(d.getNom(), d.getCategorie(), d.getProvenance(), d.getMontant(), d.getDevise(), d.getCommentaire());
    }

    public static void createCategorie(String nom, double seuil) {
        HashMap<String, String> params = new HashMap<>();
        params.put("nom", nom);
        params.put("montant", String.valueOf(seuil));
        PerformNetworkRequest request = new PerformNetworkRequest(URL_CREATE_CATEGORIE, params, CODE_POST_REQUEST);
        request.execute();
    }

    public static void createCategorie(Categorie d) {
        createCategorie(d.getNom(), d.getSeuil());
    }

    public static List<Depense> getDepenses() {
        readDepenses();
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
                try{
                    depenseList = refreshDepenseList(object.getJSONArray("transactions"));
                    depensesObservable.setDepenseList(depenseList);
                }catch (Exception e){
                    Log.d("BD","Pas de transactions");
                }

                try{
                    categoriesList = refreshCategorieList(object.getJSONArray("categories"));
                    categories.setCategorieList(categoriesList);
                }catch (Exception e){
                    Log.d("BD","Pas de categories");
                }
                //TODO Reporter la notif plus haut
//                Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
//                    Log.d("DEBUG", "object JSON categorie"+object.getJSONArray("categories"));
                Log.d("DEBUG", "object JSON transactions" + object.getJSONArray("transactions"));

                // Observable

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
