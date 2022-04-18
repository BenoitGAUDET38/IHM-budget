package com.example.fineance.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Depense implements Parcelable {
    String catégorie;
    double montant;
    String commentaire;



    protected Depense(Parcel in) {
        catégorie=in.readString();
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
        parcel.writeDouble(montant);
        parcel.writeString(commentaire);
    }
}
