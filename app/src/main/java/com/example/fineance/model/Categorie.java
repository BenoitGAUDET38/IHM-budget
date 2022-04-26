package com.example.fineance.model;

import android.os.Parcel;
import android.os.Parcelable;

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
    int id;
    String nom;
    Double seuil;
    String commentaire;

    public Categorie(String nom, Double seuil, String commentaire) {
        id = cmp++;
        this.nom = nom;
        this.seuil = seuil;
        this.commentaire = commentaire;
    }

    public Categorie(int id, String nom, Double seuil, String commentaire) {
        this.id=id;
        this.nom = nom;
        this.seuil = seuil;
        this.commentaire = commentaire;
    }

    public Categorie(Parcel in) {
        this.nom = in.readString();
        this.seuil = in.readDouble();
        commentaire = in.readString();
    }

    public String getNom() {
        return nom;
    }

    public Double getSeuil() {
        return seuil;
    }

    public String getCommentaire() {
        return commentaire;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nom);
        parcel.writeDouble(seuil);
        parcel.writeString(commentaire);
    }
}
