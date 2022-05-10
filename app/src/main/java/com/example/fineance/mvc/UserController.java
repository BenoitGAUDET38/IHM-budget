package com.example.fineance.mvc;

import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.fineance.R;
import com.example.fineance.model.User;

public class UserController {
    private final UserView view;
    private final UserActivity activity;
    private final LinearLayout layout;


    public UserController(UserView view, UserActivity activity) {
        this.view = view;
        this.activity = activity;
        this.layout = activity.findViewById(R.id.user_settings);
    }

    public void setListenersView(){
        layout.findViewById(R.id.leave_button).setOnClickListener(e -> {
            Log.d("MVC","button back l");
        });

        layout.findViewById(R.id.settings_reset).setOnClickListener(e -> {
            ModelUser.getInstance().resetUser();
        });
    }

    public void update(User u){
        Log.d("MVC","button back "+u);
        EditText name = layout.findViewById(R.id.user_name);
        EditText seuil = layout.findViewById(R.id.user_seuil);
        name.setText(u.getName());
        seuil.setText(u.getSeuil()+"");
//        currency.setText(user.getCurrency());
    }
}
