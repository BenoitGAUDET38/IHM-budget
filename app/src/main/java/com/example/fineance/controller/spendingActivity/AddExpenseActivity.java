package com.example.fineance.controller.spendingActivity;

import static com.example.fineance.controller.spendingActivity.ScanningActivity.CAMERA_REQUEST_CODE;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.fineance.R;
import com.example.fineance.controller.mainActivity.MainActivity;
import com.example.fineance.model.Depense;

public class AddExpenseActivity extends AppCompatActivity {

    private EditText nomEditText;
    private EditText categorieEditText;
    private EditText provenanceEditText;
    private EditText montantEditText;
    private EditText commentaireEditText;
    private final ActivityResultLauncher<Intent> activityResultLaunch = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null && result.getData().getExtras() != null) {
                    Bundle extras = result.getData().getExtras();
                    if (extras.containsKey("montant") && extras.get("montant") != null) {
                        this.montantEditText.setText(extras.get("montant").toString());
                    }
                    if (extras.containsKey("commentaire") && extras.get("commentaire") != null) {
                        this.commentaireEditText.setText(extras.get("commentaire").toString());
                    }
                }
            });
    private Button validerButton;
    private Button annulerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_depense);
        nomEditText = findViewById(R.id.ajout_depense_nom_edit_text);
        categorieEditText = findViewById(R.id.ajout_depense_catégorie_edit_text);
        provenanceEditText = findViewById(R.id.ajout_depense_provenance_edit_text);
        commentaireEditText = findViewById(R.id.ajout_depense_commentaire_edit_text);
        validerButton = findViewById(R.id.ajout_depense_valide_button);
        annulerButton = findViewById(R.id.ajout_depense_annuler_button);
        montantEditText = findViewById(R.id.ajout_depense_montant_editText);
        validerButton.setOnClickListener(view -> {
            Intent ajouteDepenseIntent = new Intent(this, MainActivity.class);
            Depense depense = new Depense(String.valueOf(nomEditText.getText()), String.valueOf(categorieEditText.getText()), String.valueOf(provenanceEditText.getText()), Double.parseDouble(String.valueOf(montantEditText.getText())),/*TODO Ajouter devise*/"EUR", String.valueOf(commentaireEditText.getText()));
            ajouteDepenseIntent.putExtra("depense", (Parcelable) depense);
            startActivity(ajouteDepenseIntent);
            finish();
        });
        annulerButton.setOnClickListener(this::onClick);

        ImageView ticketScanner = this.findViewById(R.id.ticketScannerImageView);
        ticketScanner.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                this.activityResultLaunch.launch(new Intent(this.getApplicationContext(), ScanningActivity.class));
            }
        });
    }

    private void onClick(View view) {
        finish();
    }
}