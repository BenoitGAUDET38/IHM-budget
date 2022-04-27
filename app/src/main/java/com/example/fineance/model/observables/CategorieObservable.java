package com.example.fineance.model.observables;

import com.example.fineance.model.Categorie;

import java.util.List;
import java.util.Observable;

public class CategorieObservable extends Observable {
    private List<Categorie> categorieList;

    public CategorieObservable(List<Categorie> categorieList) {
        this.categorieList = categorieList;
    }

    public CategorieObservable() {
    }

    public List<Categorie> getCategorieList() {
        return categorieList;
    }

    public void setCategorieList(List<Categorie> categorieList) {
        this.categorieList = categorieList;
    }

    public void update(){
        setChanged();
        notifyObservers(categorieList);
    }
}
