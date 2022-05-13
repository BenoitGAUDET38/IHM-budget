package com.example.fineance.settings.mvc;

import android.util.Log;

import com.example.fineance.modeles.User;

import java.util.Observable;

public class ModelUser extends Observable {

    private static ModelUser instance;
    private UserController controller;
    private User user;


    public ModelUser(){}

    public static ModelUser getInstance(){
        if (instance == null)
            instance = new ModelUser();
        return instance;
    }

    private void updateData() {
        setChanged();
        notifyObservers(user);

        if (controller != null) {
            controller.update(user);
        }
    }

    public void setController(UserController controller) {
        this.controller = controller;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void resetUser() {
        user = new User();
        updateData();
    }

    public void deviseUser(String d) {
        Log.d("DEBUG","Model " +d);
        user.setCurrency(d);
    }

    public User getUser() {
        return user;
    }
}
