package com.example.fineance.mvc;

import static com.example.fineance.model.User.holder;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fineance.R;
import com.example.fineance.model.User;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        UserView view = new UserView(getApplicationContext(), findViewById(R.id.user_settings));

        UserController controller = new UserController(view, this);
        ModelUser.getInstance().addObserver(view);
        view.setController(controller);
        ModelUser.getInstance().setController(controller);
        ModelUser.getInstance().setUser(new User());
    }

    public void stop(User u) {
        holder.setUser(u);
        finish();
    }
}