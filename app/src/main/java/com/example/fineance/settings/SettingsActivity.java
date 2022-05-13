package com.example.fineance.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.fineance.R;
import com.example.fineance.categories.AddCategoryActivity;
import com.example.fineance.depenses.AddExpenseActivity;
import com.example.fineance.settings.mvc.UserActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SettingsActivity extends AppCompatActivity {

    public TextView log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_settings);
        this.getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primary_blue));

        log = findViewById(R.id.last_log);
        findViewById(R.id.ajout_categorie).setOnClickListener(v -> this.startActivity(new Intent(this, AddCategoryActivity.class)));
        findViewById(R.id.ajout_depense).setOnClickListener(v -> this.startActivity(new Intent(this, AddExpenseActivity.class)));
        findViewById(R.id.leave_button).setOnClickListener(v -> finish());
    }

    public void showLogs(View view) {
        try {
            InputStream fis = openFileInput("Logs");
            BufferedReader r = new BufferedReader(new InputStreamReader(fis));

            String line;
            StringBuilder res = new StringBuilder();
            while ((line = r.readLine()) != null) {
                res.append(line).append("\n");
            }
            log.setText(res.toString());
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void modifUser(View view) {
        startActivity(new Intent(this, UserActivity.class));
    }
}