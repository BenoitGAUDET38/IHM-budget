package com.example.fineance.controller.spendingActivity;

import static com.example.fineance.model.PerformNetworkRequest.createTransaction;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fineance.R;
import com.example.fineance.controller.inputFragment.DepenseFragment;
import com.example.fineance.model.Depense;

public class AddExpenseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_depense);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new DepenseFragment()).commit();
        setListeners();
    }

    private void setListeners() {
        getSupportFragmentManager().setFragmentResultListener("transaction", this, (requestKey, result) -> {
            createTransaction((Depense) result.get(requestKey));
            finish();
        });
        getSupportFragmentManager().setFragmentResultListener("delete", this, (requestKey, result) -> {
            Log.d("DEBUG", "Travail Terminé, on plie les gaules");
            finish();
        });
    }
}