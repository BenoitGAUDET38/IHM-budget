package com.example.fineance.modeles;

import java.util.ArrayList;
import java.util.List;

public enum Devise {
    EUR("EUR", "Euro", 1, "€"),
    USD("USD", "Dollars", 1.0584, "$"),
    JPY("JPY", "Yen", 135.51, "¥"),
    CNY("CNY", "Yuan", 6.93, "元"),
    INR("INR", "Roupie indienne", 81.02, "₹"),
    GBP("GBP", "Livre britannique", 0.84, "£");

    private final String code;
    private final String name;
    private final double value;
    private final String symbole;

    Devise(String code, String name, double value, String symbole) {
        this.code = code;
        this.name = name;
        this.value = value;
        this.symbole = symbole;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public String getSymbole() {
        return symbole;
    }

    public static List<String> getCodeValues() {
        List<String> codes = new ArrayList<>();
        for (Devise d : Devise.values()) {
            codes.add(d.getCode());
        }
        return codes;
    }
}
