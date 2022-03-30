package com.example.fineance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView moneyCircleButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moneyCircleButton=findViewById(R.id.moneyCircleButton);

        moneyCircleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newActivity=new Intent(MainActivity.this, Historique.class);
                startActivity(newActivity);
            }
        });
    }
}