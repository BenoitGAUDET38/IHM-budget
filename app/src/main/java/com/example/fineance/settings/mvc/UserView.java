package com.example.fineance.settings.mvc;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fineance.R;
import com.example.fineance.modeles.User;

import java.util.Observable;
import java.util.Observer;

public class UserView implements Observer {
    private UserController controller;
    private final LinearLayout layout;
    private final Context context;


    public UserView(Context context, LinearLayout layout) {
        this.context=context;
        this.layout=layout;
    }

    public void setController(UserController controller) {
        this.controller = controller;
        this.controller.setListenersView();
    }

    @Override
    public void update(Observable observable, Object o) {
        User user = (User) o;
        TextView name = layout.findViewById(R.id.user_name);
        name.setText(user.getName());
    }
}
