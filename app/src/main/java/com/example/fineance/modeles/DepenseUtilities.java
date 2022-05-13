package com.example.fineance.modeles;

import android.util.ArrayMap;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DepenseUtilities {

    public static double getMontantTotal(List<Depense> depenseList) {
        return Math.round(depenseList.stream().map(d -> getDepenseConvertion(d)).reduce(0.0, Double::sum)*100.)/100.;
    }

    public static double getMontantTotalParCategorie(List<Depense> depenseList, int categorie) {
        return Math.round(depenseList.stream().filter(d -> d.getCategorie() == categorie)
                .map(d -> getDepenseConvertion(d))
                .reduce(0.0, Double::sum)*100.)/100.;
    }

    public static double getEuroConvertion(Depense depense) {
        return Devise.valueOf(depense.getDevise()).getValue();
    }

    public static double getDepenseConvertion(Depense depense) {
        return depense.getMontant() / getEuroConvertion(depense);
    }

    public static Map<String, Double> getDepenseParCategorie(List<Depense> depenseList) {
        Map<String, Double> map = new ArrayMap<>();

        for (Depense d : depenseList) {
            String categorie;
            if (PerformNetworkRequest.findCategorieById(d.getCategorie()) == null) {
                categorie = "Défaut";
            } else {
                categorie = PerformNetworkRequest.findCategorieById(d.getCategorie()).getNom();
            }
            if (map.containsKey(categorie)) {
                map.replace(categorie, map.get(categorie), map.get(categorie) + d.getMontant());
            } else {
                map.put(categorie, d.getMontant());
            }
        }

        return map;
    }

    public static Map<String, Double> getDepenseConvertionParCategorie(List<Depense> depenseList) {
        Map<String, Double> map = new ArrayMap<>();

        for (Depense d : depenseList) {
            String categorie;
            if (PerformNetworkRequest.findCategorieById(d.getCategorie()) == null) {
                categorie = "Défaut";
            } else {
                categorie = PerformNetworkRequest.findCategorieById(d.getCategorie()).getNom();
            }
            if (map.containsKey(categorie)) {
                map.replace(categorie, map.get(categorie), map.get(categorie) + getDepenseConvertion(d));
            } else {
                map.put(categorie, getDepenseConvertion(d));
            }
        }

        return map;
    }

    public static List<Depense> getDepenseParDuree(List<Depense> depenses, Timestamp debut, Timestamp fin) {
        List<Depense> depenseList = new ArrayList<>();
        depenses.stream().filter(depense -> depense.getDate().compareTo(debut) > 0 && depense.getDate().compareTo(fin) < 0).forEach(depense -> depenseList.add(depense));
        return depenseList;
    }

}
