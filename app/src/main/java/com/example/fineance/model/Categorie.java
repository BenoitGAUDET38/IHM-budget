package com.example.fineance.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Categorie implements Parcelable {
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
    private static int cmp;
    private int id;
    String nom;
    Double seuil;

    public Categorie(String nom, Double seuil) {
        id = cmp++;
        this.nom = nom;
        this.seuil = seuil;
    }

    public Categorie(int id, String nom, Double seuil) {
        this.id=id;
        this.nom = nom;
        this.seuil = seuil;
    }

    public Categorie(Parcel in) {
        this.id = in.readInt();
        this.nom = in.readString();
        this.seuil = in.readDouble();
    }

    public String getNom() {
        return nom;
    }

    public Double getSeuil() {
        return seuil;
    }

    public int getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(nom);
        parcel.writeDouble(seuil);
    }

    @NonNull
    @Override
    public String toString() {
        return "Categorie{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", seuil=" + seuil +
                '}';
    }
}
