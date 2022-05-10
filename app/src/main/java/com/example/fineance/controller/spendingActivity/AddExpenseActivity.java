package com.example.fineance.controller.spendingActivity;

import static com.example.fineance.controller.inputFragment.DepenseFragment.newDepenseFragment;
import static com.example.fineance.model.PerformNetworkRequest.createTransaction;
import static com.example.fineance.model.PerformNetworkRequest.deleteTransaction;
import static com.example.fineance.model.PerformNetworkRequest.updateTransaction;
import static java.util.Objects.isNull;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fineance.R;
import com.example.fineance.controller.inputFragment.DepenseFragment;
import com.example.fineance.model.Depense;
import com.example.fineance.model.notifications.Notification;
import com.example.fineance.model.notifications.notificationsFactories.AbstractNotificationFactory;
import com.example.fineance.model.notifications.notificationsFactories.HighPriorityNotificationFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AddExpenseActivity extends AppCompatActivity {
    private Depense depense = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_depense);

        Intent in = getIntent();
        if (in != null) {
            Bundle bundle = in.getExtras();
            if (!isNull(bundle)){
                depense = (Depense) bundle.get("depense");
            }

        }
        DepenseFragment d = newDepenseFragment(depense);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, d).commit();
        setListeners();
    }

    private void setListeners() {
        getSupportFragmentManager().setFragmentResultListener("transaction", this, (requestKey, result) -> {
            Depense d = (Depense) result.get(requestKey);
            String res;
            if (isNull(depense)){
                createNotification(d);
                createTransaction(d);
                res=" ajoutée";
            }
            else {
                updateTransaction(depense.getId(), d);
                res=" modifiée";
            }
            saveData(depense.getNom() +res);
            finish();
        });
        getSupportFragmentManager().setFragmentResultListener("delete", this, (requestKey, result) -> {
            if(!isNull(depense)){
                saveData(depense.getNom() +" supprimée");
                deleteTransaction(depense.getId());
            }
            finish();
        });
    }

    private void createNotification(Depense depense) {
        AbstractNotificationFactory factory = new HighPriorityNotificationFactory();
        Notification notif = factory.buildImageNotification(getApplicationContext(),
                getResources(),
                AbstractNotificationFactory.DEPENSE_IMG,
                "Nouvelle dépense", depense.getNom() + " d'une valeur de " +
                        depense.getMontant() + depense.getDevise() + " à " +
                        depense.getProvenance() + " a été ajouté !");
        notif.sendNotificationOnChannel();
    }

    public void saveData(String s) {
        FileOutputStream fos;
        s+="\n";
        try {
            fos = openFileOutput("Logs", Context.MODE_APPEND);
            fos.write(s.getBytes());
            fos.close();
            Log.d("DEBUG","Ecriture finie");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isExternalStorageWritable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return isExternalStorageWritable() || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }
}