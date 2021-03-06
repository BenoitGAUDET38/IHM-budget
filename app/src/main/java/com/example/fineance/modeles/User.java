package com.example.fineance.modeles;

import androidx.annotation.NonNull;

public class User {

    private String name;
    private int seuil;
    private String currency;
    public static final User holder = new User();

    public static User getInstance(){
        return holder;
    }

    public User() {
        name="";
        seuil=100;
        currency="EUR";
    }

    public User(String name, int seuil, String currency) {
        this.name = name;
        this.seuil = seuil;
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeuil() {
        return seuil;
    }

    public void setSeuil(int seuil) {
        this.seuil = seuil;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setUser(User u){
        setCurrency(u.getCurrency());
        setName(u.getName());
        setSeuil(u.getSeuil());
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", seuil=" + seuil +
                ", currency='" + currency + '\'' +
                '}';
    }
}
