package com.example.fineance.model;

import android.util.ArrayMap;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DepenseUtilities {

    public static double getMontantTotal(List<Depense> depenseList) {
        return Math.round(depenseList.stream().map(d -> d.getMontant() * getEuroConvertion(d)).reduce(0.0, Double::sum)*100.)/100.;
    }

    public static double getEuroConvertion(Depense depense) {
        return Devise.valueOf(depense.getDevise()).getValue();
    }

    public static Map<String, Double> getDepenseParCategorie(List<Depense> depenseList) {
        Map<String, Double> map = new ArrayMap<>();

        for (Depense d : depenseList) {
            String categorie = Objects.requireNonNull(PerformNetworkRequest.findCategorieById(d.getCategorie())).getNom();
            if (map.containsKey(categorie)) {
                map.replace(categorie, map.get(categorie), map.get(categorie) + d.getMontant());
            } else {
                map.put(categorie, d.getMontant());
            }
        }

        return map;
    }

    public static List<Depense> getDepenseParDuree(List<Depense> depenses,Timestamp debut,Timestamp fin) {
        List<Depense> depenseList=new ArrayList<>();
        System.out.println("===========================================");
        System.out.println(debut);
        System.out.println(fin);
        System.out.println(depenses);
        depenses.stream().filter(depense -> depense.getDate().compareTo(debut)>0 && depense.getDate().compareTo(fin)<0).forEach(depense -> depenseList.add(depense));
        return depenseList;
    }

}
