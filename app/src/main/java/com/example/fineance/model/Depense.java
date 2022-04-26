package com.example.fineance.model;

import static com.example.fineance.model.PerformNetworkRequest.depenseList;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.sql.Timestamp;

public class Depense implements Parcelable, Serializable {

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
    private static final int PAS_VALEUR = -1;
    private static int cmp = 0;
    String nom;
    String provenance;
    double montant;
    String devise;
    String commentaire;
    Timestamp date = new Timestamp(System.currentTimeMillis());
    int id = PAS_VALEUR;
    int categorie = PAS_VALEUR;

    public Depense(String nom, int categorie, String provenance, double montant, String devise, String commentaire) {
        this.id = cmp++;
        this.nom = nom;
        this.categorie = categorie;
        this.provenance = provenance;
        this.montant = montant;
        this.devise = devise;
        this.commentaire = commentaire;
    }

    public Depense(int id, String nom, int categorie, String provenance, double montant, String devise, String commentaire, Timestamp date) {
        this.id = id;
        this.nom = nom;
        this.categorie = categorie;
        this.provenance = provenance;
        this.montant = montant;
        this.devise = devise;
        this.commentaire = commentaire;
        this.date = date;
    }

    public Depense(Parcel in) {
        id = cmp++;
        nom = in.readString();
        categorie = in.readInt();
        provenance = in.readString();
        montant = in.readDouble();
        devise = in.readString();
        commentaire = in.readString();
        init();
        Log.d("DEBUG", "Depense 'vide': " + this);
    }

    public static double getMontantTotal() {
        return depenseList.stream().map(Depense::getMontant).reduce(0.0, Double::sum);
    }

    private void init() {
        if (nom.equals(""))
            nom = " ";
        if (provenance.equals(""))
            provenance = " ";
        if (devise.equals(""))
            devise = "EUR";
        if (commentaire.equals(""))
            commentaire = " ";

    }

    public boolean valid() {
        return this.montant != 0.0 && !this.nom.equals("") && this.id != PAS_VALEUR && this.categorie != PAS_VALEUR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nom);
        parcel.writeInt(categorie);
        parcel.writeString(provenance);
        parcel.writeDouble(montant);
        parcel.writeString(devise);
        parcel.writeString(commentaire);
//        parcel.writeLong(date.getTime());
    }

    @NonNull
    @Override
    public String toString() {
        return "Depense{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", categorie='" + categorie + '\'' +
                ", provenance='" + provenance + '\'' +
                ", montant=" + montant +
                ", devise='" + devise + '\'' +
                ", commentaire='" + commentaire + '\'' +
                ", date=" + date +
                '}';
    }

    public String getNom() {
        return nom;
    }

    public int getCategorie() {
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

    public String getDevise() {
        return devise;
    }

    public int getId() {
        return id;
    }
}
