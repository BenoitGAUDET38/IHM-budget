package com.example.fineance.controller.spendingActivity;

import static com.example.fineance.model.PerformNetworkRequest.createTransaction;
import static com.example.fineance.model.PerformNetworkRequest.deleteTransaction;
import static com.example.fineance.model.PerformNetworkRequest.updateTransaction;
import static java.util.Objects.isNull;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fineance.R;
import com.example.fineance.controller.inputFragment.DepenseFragment;
import com.example.fineance.model.Depense;

public class AddExpenseActivity extends AppCompatActivity {
    private Depense depense = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_depense);

        Intent in = getIntent();
        if (!isNull(in)) {
            Bundle bundle = in.getExtras();
            if (!isNull(bundle)){
                depense = (Depense) bundle.get("depense");
                Log.d("DEBUG","Bundle "+bundle.get("depense"));
            }

        }
        Log.d("DEBUG","Construct "+depense);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new DepenseFragment(depense)).commit();
        setListeners();
    }

    private void setListeners() {
        getSupportFragmentManager().setFragmentResultListener("transaction", this, (requestKey, result) -> {
            Depense d = (Depense) result.get(requestKey);
            if (isNull(depense))
                createTransaction(d);
            else {
                updateTransaction(depense.getId(), d);
            }

            finish();
        });
        getSupportFragmentManager().setFragmentResultListener("delete", this, (requestKey, result) -> {
            Log.d("DEBUG","Delete "+depense);
            if(!isNull(depense))
                deleteTransaction(depense.getId());
            finish();
        });
    }
}