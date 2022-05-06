package com.example.fineance.model;

import java.util.List;

public class DepenseUtilities {

    public static double getMontantTotal(List<Depense> depenseList) {
        return Math.round(depenseList.stream().map(d -> d.getMontant() * getEuroConvertion(d)).reduce(0.0, Double::sum)*100.)/100.;
    }

    public static double getEuroConvertion(Depense depense) {
        return Devise.valueOf(depense.getDevise()).getValue();
    }
}
