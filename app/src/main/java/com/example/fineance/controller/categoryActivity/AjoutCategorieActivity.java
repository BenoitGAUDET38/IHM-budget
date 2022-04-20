package com.example.fineance.controller.categoryActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fineance.R;
import com.example.fineance.controller.mainActivity.MainActivity;
import com.example.fineance.model.Categorie;

public class AjoutCategorieActivity extends AppCompatActivity {

    EditText nomEditText;
    EditText seuilEditText;
    EditText commentaireEditText;
    Button validerButton;
    Button annulerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_categorie);
        nomEditText = findViewById(R.id.ajout_categorie_nom_edit_text);
        seuilEditText = findViewById(R.id.ajout_categorie_seuil_edit_text);
        commentaireEditText = findViewById(R.id.ajout_categorie_commentaire_edit_text);
        validerButton = findViewById(R.id.ajout_categorie_valide_button);
        annulerButton = findViewById(R.id.ajout_categorie_annuler_button);
        validerButton.setOnClickListener(view -> {
            Intent ajouteDepenseIntent = new Intent(AjoutCategorieActivity.this, MainActivity.class);
            Categorie categorie = new Categorie(String.valueOf(nomEditText.getText()), Double.parseDouble(String.valueOf(seuilEditText.getText())), String.valueOf(commentaireEditText.getText()));
            ajouteDepenseIntent.putExtra("categorie", categorie);
            finish();
        });
    }

}