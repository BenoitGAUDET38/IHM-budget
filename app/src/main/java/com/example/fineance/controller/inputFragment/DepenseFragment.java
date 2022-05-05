package com.example.fineance.controller.inputFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.fineance.R;
import com.example.fineance.model.Depense;

public class DepenseFragment extends Fragment{
    private EditText nomEditText;
    private EditText categorieEditText;
    private EditText provenanceEditText;
    private EditText commentaireEditText;
    private Button validerButton;
    private Button annulerButton;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_depense, container, false);
        validerButton = view.findViewById(R.id.ajout_depense_valide_button);
        annulerButton = view.findViewById(R.id.ajout_depense_annuler_button);
        nomEditText = view.findViewById(R.id.ajout_depense_nom_edit_text);
        categorieEditText = view.findViewById(R.id.ajout_depense_catégorie_edit_text);
        provenanceEditText = view.findViewById(R.id.ajout_depense_provenance_edit_text);
        commentaireEditText = view.findViewById(R.id.ajout_depense_commentaire_edit_text);
        view.findViewById(R.id.ajout_depense_annuler_button).setOnClickListener(viewRemove -> {
            Bundle result = new Bundle();
            result.putParcelable("delete",null);
            getParentFragmentManager().setFragmentResult("delete",result);
        });
        view.findViewById(R.id.ajout_depense_valide_button).setOnClickListener(viewClicked -> {
            Depense d = new Depense("Fragment",0,"To",20,"USD","Ajout des fragments dynamiques :p");
            Bundle result = new Bundle();
            result.putParcelable("transaction",d);
            getParentFragmentManager().setFragmentResult("transaction",result);
        });
        return view;
    }
}