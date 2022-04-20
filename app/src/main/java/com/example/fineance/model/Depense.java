package com.example.fineance.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Depense implements Parcelable {
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
    String categorie;
    String provenanace;
    double montant;
    String commentaire;


    public Depense(String categorie, String provenanace, int montant, String commentaire) {
        this.categorie = categorie;
        this.provenanace = provenanace;
        this.montant = montant;
        this.commentaire = commentaire;
    }

    public Depense(Parcel in) {
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
        parcel.writeString(categorie);
        parcel.writeString(provenanace);
        parcel.writeDouble(montant);
        parcel.writeString(commentaire);
    }
}
