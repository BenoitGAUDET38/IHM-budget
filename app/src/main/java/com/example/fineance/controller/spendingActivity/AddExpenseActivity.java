package com.example.fineance.controller.spendingActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fineance.R;
import com.example.fineance.controller.mainActivity.MainActivity;
import com.example.fineance.model.Depense;

public class AddExpenseActivity extends AppCompatActivity {

    EditText categorieEditText;
    EditText provenanceEditText;
    EditText montantEditText;
    EditText commentaireEditText;
    Button validerButton;
    Button annulerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_depense);
        categorieEditText = findViewById(R.id.ajout_depense_catégorie_edit_text);
        provenanceEditText = findViewById(R.id.ajout_depense_provenance_edit_text);
        commentaireEditText = findViewById(R.id.ajout_depense_commentaire_edit_text);
        validerButton = findViewById(R.id.ajout_depense_valide_button);
        annulerButton = findViewById(R.id.ajout_depense_annuler_button);
        montantEditText = findViewById(R.id.ajout_depense_montant_editText);
        validerButton.setOnClickListener(view -> {
            Intent ajouteDepenseIntent = new Intent(AddExpenseActivity.this, MainActivity.class);
            Depense depense = new Depense(String.valueOf(categorieEditText.getText()), String.valueOf(provenanceEditText.getText()), Integer.parseInt(String.valueOf(montantEditText.getText())), String.valueOf(commentaireEditText.getText()));
            ajouteDepenseIntent.putExtra("depense", depense);
            finish();
        });

        ImageView ticketScanner = this.findViewById(R.id.ticketScannerImageView);
        ticketScanner.setOnClickListener(view -> {
            Intent intent = new Intent(this.getApplicationContext(), ScanningActivity.class);
            this.startActivity(intent);
        });
    }
}