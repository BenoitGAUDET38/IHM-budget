package com.example.fineance.observables;

import com.example.fineance.modeles.Categorie;

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
        update();
    }

    public void update(){
        setChanged();
        notifyObservers(categorieList);
    }
}
