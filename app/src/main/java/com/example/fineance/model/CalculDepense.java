package com.example.fineance.model;

import java.util.List;

public class CalculDepense {

    public static double getTotalDepenses(List<Depense> depenseList) {
        double res = 0;
        for (Depense depense : depenseList) {
            res += depense.getMontant();
        }
        return res;
    }
}
