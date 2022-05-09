package com.example.fineance.controller.categoryActivity;

import static java.util.Objects.isNull;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fineance.R;
import com.example.fineance.controller.ListFragment.DepenseListFragment;
import com.example.fineance.model.Depense;

import java.util.List;

public class DisplayDepenseActivity extends AppCompatActivity {
    private TextView title;
    private List<Depense> depenseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_depense);

        title = findViewById(R.id.title);
        title.setText("Categorie");

        Intent in = getIntent();
        if (!isNull(in)) {
            Bundle bundle = in.getExtras();
            if (!isNull(bundle)) {
                depenseList = (List<Depense>) bundle.get("depenses");
                title.setText("Categorie : "+bundle.get("categorie"));
//                Log.d("DEBUG","Bundle "+categorie);
            }
        }
        DepenseListFragment f = DepenseListFragment.newDepenseList(depenseList);
        getSupportFragmentManager().beginTransaction().replace(R.id.list_depenses, f).commit();
    }
}