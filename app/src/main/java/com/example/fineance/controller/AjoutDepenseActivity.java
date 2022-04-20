package com.example.fineance.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.fineance.R;
import com.example.fineance.model.Depense;

public class AjoutDepenseActivity extends AppCompatActivity {
    EditText catégorieEditText;
    EditText provenanceEditText;
    EditText montantEditText;
    EditText commentaireEditText;
    Button validerButton;
    Button annulerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_depense);
        catégorieEditText=findViewById(R.id.ajout_depense_catégorie_edit_text);
        provenanceEditText=findViewById(R.id.ajout_depense_provenance_edit_text);
        commentaireEditText=findViewById(R.id.ajout_depense_commentaire_edit_text);
        validerButton=findViewById(R.id.ajout_depense_valide_button);
        annulerButton=findViewById(R.id.ajout_depense_annuler_button);
        montantEditText=findViewById(R.id.ajout_depense_montant_editText);
        validerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ajouteDepenseIntent=new Intent(AjoutDepenseActivity.this,MainActivity.class);
                Depense depense= new Depense(String.valueOf(catégorieEditText.getText()),String.valueOf(provenanceEditText.getText()),Integer.parseInt(String.valueOf(montantEditText.getText())),String.valueOf(commentaireEditText.getText()));
                ajouteDepenseIntent.putExtra("depense", (Parcelable) depense);
                finish();
            }
        });
    }
}