package com.example.fineance.depenses;

import static android.app.Activity.RESULT_OK;
import static com.example.fineance.depenses.ScanningActivity.CAMERA_REQUEST_CODE;
import static com.example.fineance.modeles.PerformNetworkRequest.findCategorieById;
import static com.example.fineance.modeles.PerformNetworkRequest.findCategorieByName;
import static com.example.fineance.modeles.PerformNetworkRequest.getCategories;
import static java.util.Objects.isNull;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.fineance.R;
import com.example.fineance.modeles.Categorie;
import com.example.fineance.modeles.Depense;
import com.example.fineance.modeles.Devise;

import java.util.List;
import java.util.stream.Collectors;

@RequiresApi(api = Build.VERSION_CODES.R)
public class EditDepenseFragment extends Fragment {

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
    private Spinner categorieEditText;
    private EditText provenanceEditText;
    private EditText commentaireEditText;
    private Button validerButton;
    private Button annulerButton;
    private EditText montantEditText;
    private Spinner currencySpinner;

    private Depense depense;
    private List<Categorie> categorieList = getCategories();
    private String deviseCode = "EUR";
    private List<String> deviseCodeList = Devise.getCodeValues();

    public EditDepenseFragment() {
        // Required empty public constructor
    }

    public static EditDepenseFragment newDepenseFragment(Depense d) {
        EditDepenseFragment fragment = new EditDepenseFragment();
        Bundle args = new Bundle();
        args.putParcelable("depense", d);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            depense = getArguments().getParcelable("depense");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_depense, container, false);
        validerButton = view.findViewById(R.id.ajout_depense_valide_button);
        annulerButton = view.findViewById(R.id.ajout_depense_annuler_button);
        montantEditText = view.findViewById(R.id.ajout_depense_montant_editText);
        nomEditText = view.findViewById(R.id.ajout_depense_nom_edit_text);
        currencySpinner = view.findViewById(R.id.ajout_depense_spinner);

        categorieEditText = view.findViewById(R.id.ajout_depense_cat??gorie_edit_text);
        List<String> categories = categorieList.stream().map(Categorie::getNom).collect(Collectors.toList());
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categories);

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        categorieEditText.setAdapter(spinnerArrayAdapter);

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

        setupCurrencySpinner();
        setValues();

        return view;
    }

    private void setValues() {
        if (!isNull(depense)) {
            nomEditText.setText(depense.getNom());
            montantEditText.setText(String.valueOf(depense.getMontant()));
            categorieEditText.setSelection(categorieList.indexOf(findCategorieById(depense.getCategorie())));
            provenanceEditText.setText(depense.getProvenance());
            commentaireEditText.setText(depense.getCommentaire());
            currencySpinner.setSelection(deviseCodeList.indexOf(depense.getDevise()));
            annulerButton.setText(R.string.delete);
            validerButton.setText(R.string.update);
        }
    }

    private Depense retrieveDepenseFromForm() {
        String name = String.valueOf(nomEditText.getText());
        Categorie c = findCategorieByName(String.valueOf(categorieEditText.getSelectedItem()));
        int categorie = (c != null ? c.getId() : 0);
        String provenance = String.valueOf(provenanceEditText.getText());
        double montant = Double.parseDouble(String.valueOf(montantEditText.getText()));
        String devise = deviseCode;
        String commentaire = String.valueOf(commentaireEditText.getText());
        return new Depense(name, categorie, provenance, montant, devise, commentaire);
    }

    private void setupCurrencySpinner() {
        currencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                deviseCode = deviseCodeList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, deviseCodeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinner.setAdapter(adapter);
    }

}