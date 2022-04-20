package com.example.fineance.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Depense implements Parcelable, Serializable {
    String catégorie;
    String provenanace;
    double montant;
    String commentaire;


    public Depense(String catégorie, String provenanace, int montant, String commentaire) {
        this.catégorie=catégorie;
        this.provenanace=provenanace;
        this.montant=montant;
        this.commentaire=commentaire;
    }


    public Depense(Parcel in) {
        catégorie=in.readString();
        provenanace=in.readString();
        montant=in.readDouble();
        commentaire=in.readString();
    }

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(catégorie);
        parcel.writeString(provenanace);
        parcel.writeDouble(montant);
        parcel.writeString(commentaire);
    }

    @Override
    public String toString() {
        return "Depense{" +
                "catégorie='" + catégorie + '\'' +
                ", provenanace='" + provenanace + '\'' +
                ", montant=" + montant +
                ", commentaire='" + commentaire + '\'' +
                '}';
    }
}
