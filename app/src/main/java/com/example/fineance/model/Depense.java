package com.example.fineance.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;
import java.sql.Timestamp;

public class Depense implements Parcelable, Serializable {

    private static int cmp;

    int id;
    String nom;
    String categorie;
    String provenance;
    double montant;
    String devise;
    String commentaire;
    Timestamp date = new Timestamp(System.currentTimeMillis());

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

    public Depense(int id, String nom, String categorie, String provenance, double montant, String devise, String commentaire,Timestamp date) {
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
        id=cmp++;
        nom=in.readString();
        categorie = in.readString();
        provenance = in.readString();
        montant = in.readDouble();
        devise = in.readString();
        commentaire = in.readString();
        init();
        Log.d("DEBUG", "Depense 'vide': "+this);
    }

    private void init() {
        if(nom.equals(""))
            nom = " ";
        if(categorie.equals(""))
            categorie = " ";
        if(provenance.equals(""))
            provenance = " ";
        if(devise.equals(""))
            devise = "EUR";
        if(commentaire.equals(""))
            commentaire = " ";

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
        parcel.writeString(devise);
        parcel.writeString(commentaire);
//        parcel.writeLong(date.getTime());
    }

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

    public String getDevise() {
        return devise;
    }

    public int getId() {
        return id;
    }
}
