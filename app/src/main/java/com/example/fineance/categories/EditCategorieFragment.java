package com.example.fineance.categories;

import static java.util.Objects.isNull;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.fineance.R;
import com.example.fineance.modeles.Categorie;

public class EditCategorieFragment extends Fragment {

    public EditCategorieFragment() {
        // Required empty public constructor
    }

    public EditCategorieFragment(Categorie c) {
        // Required empty public constructor
        categorie=c;
    }

    private EditText nomEditText;
    private EditText seuilEditText;
    private Button validerButton;
    private Button annulerButton;

    private Categorie categorie;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            validerButton.setText(getArguments().getString("ADD"));
            annulerButton.setText(getArguments().getString("REMOVE"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_categorie2, container, false);
        validerButton = view.findViewById(R.id.valide_button);
        annulerButton = view.findViewById(R.id.annuler_buton);
        seuilEditText = view.findViewById(R.id.categorie_seuil);
        nomEditText = view.findViewById(R.id.categorie_name);

        view.findViewById(R.id.annuler_buton).setOnClickListener(viewRemove -> {
            Bundle result = new Bundle();
            result.putParcelable("delete", null);
            getParentFragmentManager().setFragmentResult("delete", result);
        });
        view.findViewById(R.id.valide_button).setOnClickListener(viewClicked -> {
            Bundle result = new Bundle();
            result.putParcelable("categorie", retrieveDepenseFromForm());
            getParentFragmentManager().setFragmentResult("categorie", result);
        });
        setValues();


        return view;
    }

    private void setValues() {
        if (!isNull(categorie)) {
            nomEditText.setText(categorie.getNom());
            seuilEditText.setText(String.valueOf(categorie.getSeuil()));
            annulerButton.setText(R.string.delete);
            validerButton.setText(R.string.update);
        }
    }

    private Categorie retrieveDepenseFromForm() {
        String name = String.valueOf(nomEditText.getText());
        double seuil = Double.parseDouble(String.valueOf(seuilEditText.getText()));
        return new Categorie(name, seuil);
    }
}