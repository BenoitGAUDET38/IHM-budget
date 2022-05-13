package com.example.fineance.observables;

import com.example.fineance.modeles.Depense;

import java.util.List;
import java.util.Observable;

public class DepenseObservable extends Observable {
    private List<Depense> depenseList;

    public DepenseObservable(List<Depense> depenseList) {
        this.depenseList = depenseList;
        update();
    }

    public DepenseObservable() {
    }

    public List<Depense> getDepenseList() {
        return depenseList;
    }

    public void setDepenseList(List<Depense> depenseList) {
        this.depenseList = depenseList;
        update();
    }

    private void update(){
        setChanged();
        notifyObservers(depenseList);
    }
}
