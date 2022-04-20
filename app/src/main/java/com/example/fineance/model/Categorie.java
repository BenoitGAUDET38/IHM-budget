package com.example.fineance.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Categorie implements Parcelable{
    String nom;
    Double seuil;
    String Commentaire;


    public Categorie(String nom, Double seuil, String commentaire) {
        this.nom = nom;
        this.seuil = seuil;
        Commentaire = commentaire;
    }
    public Categorie(Parcel in) {
        this.nom = in.readString();
        this.seuil = in.readDouble();
        Commentaire = in.readString();
    }

    public static final Parcelable.Creator<Categorie> CREATOR = new Parcelable.Creator<Categorie>() {
        @Override
        public Categorie createFromParcel(Parcel in) {
            return new Categorie(in);
        }
        @Override
        public Categorie[] newArray(int size) {
            return new Categorie[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nom);
        parcel.writeDouble(seuil);
        parcel.writeString(Commentaire);
    }


}
