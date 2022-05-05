package com.example.fineance.controller.spendingActivity;

import static com.example.fineance.controller.spendingActivity.ScanningActivity.CAMERA_REQUEST_CODE;
import static com.example.fineance.model.PerformNetworkRequest.createTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.fineance.R;
import com.example.fineance.controller.inputFragment.DepenseFragment;
import com.example.fineance.model.Depense;

public class AddExpenseActivity extends AppCompatActivity{


    private EditText montantEditText;
    private final ActivityResultLauncher<Intent> activityResultLaunch = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null && result.getData().getExtras() != null) {
                    Bundle extras = result.getData().getExtras();
                    if (extras.containsKey("montant") && extras.get("montant") != null) {
                        this.montantEditText.setText(extras.get("montant").toString());
                    }
//                    if (extras.containsKey("commentaire") && extras.get("commentaire") != null) {
//                        this.commentaireEditText.setText(extras.get("commentaire").toString());
//                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_depense);
        montantEditText = findViewById(R.id.ajout_depense_montant_editText);
        /*validerButton.setOnClickListener(view -> {
            Intent ajouteDepenseIntent = new Intent(this, MainActivity.class);
            Depense depense = new Depense(*//*String.valueOf(nomEditText.getText())*//*"", *//*Integer.parseInt(String.valueOf(categorieEditText.getText()))*//*0, *//*String.valueOf(provenanceEditText.getText())*//*"", Double.parseDouble(String.valueOf(montantEditText.getText())), "EUR", ""*//*String.valueOf(commentaireEditText.getText())*//*);
            ajouteDepenseIntent.putExtra("depense", (Parcelable) depense);
            startActivity(ajouteDepenseIntent);
            finish();
        });*/

        ImageView ticketScanner = this.findViewById(R.id.ticketScannerImageView);
        ticketScanner.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                this.activityResultLaunch.launch(new Intent(this.getApplicationContext(), ScanningActivity.class));
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new DepenseFragment()).commit();

        getSupportFragmentManager().setFragmentResultListener("transaction", this, (requestKey, result) -> {
            createTransaction((Depense) result.get(requestKey));
            finish();
        });

        getSupportFragmentManager().setFragmentResultListener("delete", this, (requestKey, result) -> {
            //createTransaction((Depense) result.get(requestKey));
            Log.d("DEBUG","Travail Terminé, on plie les gaules");
            finish();
        });
    }
}