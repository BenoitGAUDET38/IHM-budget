package com.example.fineance.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Depense implements Parcelable, Serializable {
    String nom;
    String categorie;
    String provenanace;
    double montant;
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

    public Depense(String nom,String categorie, String provenanace, double montant, String commentaire) {
        this.nom=nom;
        this.categorie = categorie;
        this.provenanace = provenanace;
        this.montant = montant;
        this.commentaire = commentaire;
    }

    public Depense(Parcel in) {
        nom=in.readString();
        categorie = in.readString();
        provenanace = in.readString();
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
        parcel.writeString(provenanace);
        parcel.writeDouble(montant);
        parcel.writeString(commentaire);
    }

    @Override
    public String toString() {
        return "Depense{" +
                "nom='" + nom + '\'' +
                ", categorie='" + categorie + '\'' +
                ", provenanace='" + provenanace + '\'' +
                ", montant=" + montant +
                ", commentaire='" + commentaire + '\'' +
                '}';
    }

    public String getNom() { return nom; }

    public String getCategorie() {
        return categorie;
    }

    public String getProvenanace() {
        return provenanace;
    }

    public double getMontant() {
        return montant;
    }

    public String getCommentaire() {
        return commentaire;
    }
}
