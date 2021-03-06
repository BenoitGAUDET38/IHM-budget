package com.example.fineance.modeles;

import static com.example.fineance.modeles.DepenseUtilities.getDepenseConvertion;

import android.os.AsyncTask;
import android.util.Log;

import com.example.fineance.observables.CategorieObservable;
import com.example.fineance.observables.DepenseObservable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
    //TODO Decouper side-functions and execute
    public static final DepenseObservable depensesObservable = new DepenseObservable();
    public static final CategorieObservable categoriesObservable = new CategorieObservable();

    public static final int CODE_GET_REQUEST = 1024;
    public static final int CODE_POST_REQUEST = 1025;

    private static final String ROOT_URL = "https://fineance.000webhostapp.com/FineAnceApi/Api.php?apicall=";

    public static final String URL_CREATE_DEPENSE = ROOT_URL + "createtransaction";
    public static final String URL_GET_TRANSACTIONS = ROOT_URL + "gettransactions";
    public static final String URL_UPDATE_TRANSACTION = ROOT_URL + "updatetransaction";
    public static final String URL_DELETE_TRANSACTION = ROOT_URL + "deletetransaction&id=";
    public static final String URL_CREATE_CATEGORIE = ROOT_URL + "createcategorie";
    public static final String URL_GET_CATEGORIES = ROOT_URL + "getcategories";
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
                    obj.getDouble("seuil")
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

    public static void updateTransaction(int id, Depense depense) {
        updateTransaction(id, depense.nom, depense.getMontant(), depense.getDevise(), depense.getCategorie() + "", depense.getCommentaire(), depense.getProvenance());
    }

    public static void updateCategorie(int id, Categorie categorie) {
        updateCategorie(id, categorie.nom, categorie.seuil);
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
        Log.d("DB", "Ajout d'une depense :" + d);
        createTransaction(d.getNom(), d.getCategorie(), d.getProvenance(), d.getMontant(), d.getDevise(), d.getCommentaire());
    }

    public static void createCategorie(String nom, double seuil) {
        HashMap<String, String> params = new HashMap<>();
        params.put("nom", nom);
        params.put("seuil", String.valueOf(seuil));
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

    public static List<Categorie> getCategories() {
        readCategories();
        return categoriesList;
    }

    //when the task started displaying a progressbar
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d("BD", "Requete demarr??e");
    }

    //this method will give the response from the request
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("BD", "Requete execut??e");
        try {
            JSONObject object = new JSONObject(s);
            if (!object.getBoolean("error")) {
                try {
                    depenseList = refreshDepenseList(object.getJSONArray("transactions"));
                    depensesObservable.setDepenseList(depenseList);
                } catch (Exception e) {
                    Log.d("BD", "Pas de transactions");
                }

                try {
                    categoriesList = refreshCategorieList(object.getJSONArray("categories"));
                    categoriesObservable.setCategorieList(categoriesList);
                    Log.d("BD", "Categories");
                } catch (Exception e) {
                    Log.d("BD", "Pas de categories");
                }
            }else
                Log.d("RUSH","error");
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

    /**
     * @param id id of the categorie
     * @return categorie with the id, null if id's categorie doesn't exist
     */
    public static Categorie findCategorieById(int id){
        for(Categorie c : categoriesList){
            if(c.getId() == id)
                return c;
        }
        return null;
    }

    public static double sumCategorie(int id){
        double res =0;
        for (Depense d: depenseList) {
            if(d.getCategorie() == id)
                res+=getDepenseConvertion(d);
        }
        return res;
    }

    /**
     * @param name name of the categorie
     * @return categorie with the name, null if name's categorie doesn't exist
     */
    public static Categorie findCategorieByName(String name){
        for(Categorie c : categoriesList){
            if(c.getNom().equals(name))
                return c;
        }
        return null;
    }

    public static List<Depense> depensesByCategorie(int id){
        return depenseList.stream().filter(e -> e.getCategorie() == id).collect(Collectors.toList());
    }
}
