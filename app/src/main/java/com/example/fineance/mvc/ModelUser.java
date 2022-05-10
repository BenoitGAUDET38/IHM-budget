package com.example.fineance.mvc;

import com.example.fineance.model.User;

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

    public void resetUser() {
        user = new User();
        updateData();
    }
}
