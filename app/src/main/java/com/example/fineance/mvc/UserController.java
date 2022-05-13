package com.example.fineance.mvc;

import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.fineance.R;
import com.example.fineance.model.Devise;
import com.example.fineance.model.User;

import java.util.List;

public class UserController {
    private final UserView view;
    private final UserActivity activity;
    private final LinearLayout layout;
    private Spinner spinner;
    EditText name;
    EditText seuil;
    private String deviseCode = "EUR";
    private List<String> deviseCodeList = Devise.getCodeValues();


    public UserController(UserView view, UserActivity activity) {
        this.view = view;
        this.activity = activity;
        this.layout = activity.findViewById(R.id.user_settings);
    }

    public void setListenersView(){
        layout.findViewById(R.id.leave_button).setOnClickListener(e -> {
            updateUser(name.getText(),seuil.getText(),spinner.getSelectedItem().toString());
            activity.stop(ModelUser.getInstance().getUser());
        });

        layout.findViewById(R.id.settings_reset).setOnClickListener(e -> ModelUser.getInstance().resetUser());
        spinner = layout.findViewById(R.id.user_currency);
        name = layout.findViewById(R.id.user_name);
        seuil = layout.findViewById(R.id.user_seuil);

        name.setOnClickListener(e -> updateUser(name.getText(),seuil.getText(),spinner.getSelectedItem().toString()));
        seuil.setOnClickListener(e -> updateUser(name.getText(),seuil.getText(),spinner.getSelectedItem().toString()));
        ArrayAdapter<String> adapter = new ArrayAdapter(activity, android.R.layout.simple_spinner_dropdown_item, deviseCodeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
               updateUser(name.getText(),seuil.getText(),deviseCodeList.get(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


    }

    private void updateUser(Editable name, Editable seuil, String currency) {
        ModelUser.getInstance().setUser(new User(name.toString(),
                Integer.parseInt(seuil.toString()),
                currency));
    }

    public void update(User u){
        name.setText(u.getName());
        seuil.setText(String.valueOf(u.getSeuil()));
        int i=0;
        for(String s :deviseCodeList){
            if (s.equals(u.getCurrency()))
                break;
            i++;
        }
        spinner.setSelection(i);
    }


}
