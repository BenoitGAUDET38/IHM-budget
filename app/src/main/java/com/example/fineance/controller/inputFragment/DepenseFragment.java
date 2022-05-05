package com.example.fineance.controller.inputFragment;

import static android.app.Activity.RESULT_OK;
import static com.example.fineance.controller.spendingActivity.ScanningActivity.CAMERA_REQUEST_CODE;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.fineance.R;
import com.example.fineance.controller.spendingActivity.ScanningActivity;
import com.example.fineance.model.Depense;

public class DepenseFragment extends Fragment {

    private final ActivityResultLauncher<Intent> activityResultLaunch = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null && result.getData().getExtras() != null) {
                    Bundle extras = result.getData().getExtras();
                    if (extras.containsKey("montant") && extras.get("montant") != null) {
                        this.montantEditText.setText(extras.get("montant").toString());
                    }
                }
            });

    /**
     * TODO Date ?
     **/

    private EditText nomEditText;
    private EditText categorieEditText;
    private EditText provenanceEditText;
    private EditText commentaireEditText;
    private Button validerButton;
    private Button annulerButton;
    private EditText montantEditText;

    public DepenseFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            validerButton.setText(getArguments().getString("ADD"));
            annulerButton.setText(getArguments().getString("REMOVE"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_depense, container, false);
        validerButton = view.findViewById(R.id.ajout_depense_valide_button);
        annulerButton = view.findViewById(R.id.ajout_depense_annuler_button);
        montantEditText = view.findViewById(R.id.ajout_depense_montant_editText);
        nomEditText = view.findViewById(R.id.ajout_depense_nom_edit_text);
        categorieEditText = view.findViewById(R.id.ajout_depense_catégorie_edit_text);
        provenanceEditText = view.findViewById(R.id.ajout_depense_provenance_edit_text);
        commentaireEditText = view.findViewById(R.id.ajout_depense_commentaire_edit_text);

        ImageView ticketScanner = view.findViewById(R.id.ticketScannerImageView);
        ticketScanner.setOnClickListener(viewScanner -> {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
            }
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                this.activityResultLaunch.launch(new Intent(getActivity().getApplicationContext(), ScanningActivity.class));
            }
        });

        view.findViewById(R.id.ajout_depense_annuler_button).setOnClickListener(viewRemove -> {
            Bundle result = new Bundle();
            result.putParcelable("delete", null);
            getParentFragmentManager().setFragmentResult("delete", result);
        });
        view.findViewById(R.id.ajout_depense_valide_button).setOnClickListener(viewClicked -> {
            Bundle result = new Bundle();
            result.putParcelable("transaction", retrieveDepenseFromForm());
            getParentFragmentManager().setFragmentResult("transaction", result);
        });
        return view;
    }

    private Depense retrieveDepenseFromForm() {
        String name = String.valueOf(nomEditText.getText());
        int categorie = Integer.parseInt(String.valueOf(categorieEditText.getText()));
        String provenance = String.valueOf(provenanceEditText.getText());
        double montant = Double.parseDouble(String.valueOf(montantEditText.getText()));
        String devise = "USD";
        String commentaire = String.valueOf(commentaireEditText.getText());
        return new Depense(name, categorie, provenance, montant, devise, commentaire);
    }
}