package com.example.fineance.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fineance.R;

public class ListeTransaction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_transaction);

        ImageView ticketScanner = this.findViewById(R.id.ticketScanner);
        ticketScanner.setOnClickListener(view -> {
            Intent intent = new Intent(this.getApplicationContext(), ScanningActivity.class);
            this.startActivity(intent);
        });
    }
}