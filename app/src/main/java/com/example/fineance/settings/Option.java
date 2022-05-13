package com.example.fineance.settings;

import android.view.View;

public class Option {

    private final String name;
    private final View view;

    public Option(String name, View view) {
        this.name = name;
        this.view = view;
    }

    public String getName() {
        return name;
    }

    public View getView() {
        return view;
    }
}
