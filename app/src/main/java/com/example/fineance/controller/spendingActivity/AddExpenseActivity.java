package com.example.fineance.controller.spendingActivity;

import static com.example.fineance.controller.inputFragment.DepenseFragment.newDepenseFragment;
import static com.example.fineance.model.PerformNetworkRequest.createTransaction;
import static com.example.fineance.model.PerformNetworkRequest.deleteTransaction;
import static com.example.fineance.model.PerformNetworkRequest.depenseList;
import static com.example.fineance.model.PerformNetworkRequest.getCategories;
import static com.example.fineance.model.PerformNetworkRequest.updateTransaction;
import static java.util.Objects.isNull;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fineance.R;
import com.example.fineance.controller.inputFragment.DepenseFragment;
import com.example.fineance.model.Categorie;
import com.example.fineance.model.Depense;
import com.example.fineance.model.DepenseUtilities;
import com.example.fineance.model.notifications.Notification;
import com.example.fineance.model.notifications.notificationsFactories.AbstractNotificationFactory;
import com.example.fineance.model.notifications.notificationsFactories.HighPriorityNotificationFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

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
                createTransaction(d);
                res=" ajoutée";
            }
            else {
                updateTransaction(depense.getId(), d);
                res=" modifiée";
            }
            createNotification(d);
            saveData(d.getNom() +res);
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
        List<Categorie> categories = getCategories();
        double currentSeuil = 100;
        for (Categorie c : categories) {
            if (c.getId() == depense.getCategorie()) {
                currentSeuil = c.getSeuil();
            }
        }
        if (currentSeuil <= DepenseUtilities.getMontantTotalParCategorie(depenseList, depense.getCategorie()) + DepenseUtilities.getDepenseConvertion(depense)) {
            Notification notifPrevention = factory.buildBasicNotification(getApplicationContext(),
                    "ATTENTION SEUIL",
                    "En ajoutant " + depense.getNom() + " d'une valeur de " +
                            depense.getMontant() + depense.getDevise() + ", vous avez dépassé votre seuil fixé à " + currentSeuil + "EUR.");
            notifPrevention.sendNotificationOnChannel();
        }
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
}