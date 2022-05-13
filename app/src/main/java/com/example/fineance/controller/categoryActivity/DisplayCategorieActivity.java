package com.example.fineance.controller.categoryActivity;

import static com.example.fineance.model.PerformNetworkRequest.createCategorie;
import static com.example.fineance.model.PerformNetworkRequest.deleteCategorie;
import static com.example.fineance.model.PerformNetworkRequest.updateCategorie;
import static java.util.Objects.isNull;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fineance.R;
import com.example.fineance.controller.inputFragment.CategorieFragment;
import com.example.fineance.model.Categorie;

public class DisplayCategorieActivity extends AppCompatActivity {
    private Categorie categorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_categorie);

        Intent in = getIntent();
        if (!isNull(in)) {
            Bundle bundle = in.getExtras();
            if (!isNull(bundle)) {
                categorie = (Categorie) bundle.get("categorie");
            }
            }
        getSupportFragmentManager().beginTransaction().replace(R.id.categorie_edit, new CategorieFragment(categorie)).commit();

        getSupportFragmentManager().setFragmentResultListener("categorie", this, (requestKey, result) -> {
            Categorie d = (Categorie) result.get(requestKey);
            if (isNull(categorie))
                createCategorie(d);
            else {
                updateCategorie(categorie.getId(), d);
            }
            finish();
        });
        getSupportFragmentManager().setFragmentResultListener("delete", this, (requestKey, result) -> {
            if (!isNull(categorie))
                deleteCategorie(categorie.getId());
            finish();
        });
    }

}