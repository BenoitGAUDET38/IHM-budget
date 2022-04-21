package com.example.fineance.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Depense implements Parcelable, Serializable {

    private static int cmp;

    int id;
    String nom;
    String categorie;
    String provenance;
    double montant;
    String devise;
    String commentaire;



    public static final Creator<Depense> CREATOR = new Creator<Depense>() {
        @Override
        public Depense createFromParcel(Parcel in) {
            return new Depense(in);
        }

        @Override
        public Depense[] newArray(int size) {
            return new Depense[size];
        }
    };

    public Depense(String nom, String categorie, String provenance, double montant, String devise,String commentaire) {
        this.id = cmp++;
        this.nom = nom;
        this.categorie = categorie;
        this.provenance = provenance;
        this.montant = montant;
        this.devise = devise;
        this.commentaire = commentaire;
    }

    public Depense(Parcel in) {
        id=cmp++;
        nom=in.readString();
        categorie = in.readString();
        provenance = in.readString();
        montant = in.readDouble();
        commentaire = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nom);
        parcel.writeString(categorie);
        parcel.writeString(provenance);
        parcel.writeDouble(montant);
        parcel.writeString(commentaire);
    }

    @NonNull
    @Override
    public String toString() {
        return "Depense{" +
                "nom='" + nom + '\'' +
                ", categorie='" + categorie + '\'' +
                ", provenanace='" + provenance + '\'' +
                ", montant=" + montant +
                ", commentaire='" + commentaire + '\'' +
                '}';
    }

    public String getNom() { return nom; }

    public String getCategorie() {
        return categorie;
    }

    public String getProvenance() {
        return provenance;
    }

    public double getMontant() {
        return montant;
    }

    public String getCommentaire() {
        return commentaire;
    }
}
