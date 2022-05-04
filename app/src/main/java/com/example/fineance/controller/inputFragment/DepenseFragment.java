package com.example.fineance.controller.inputFragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.fineance.R;
import com.example.fineance.model.Depense;

public class DepenseFragment extends Fragment implements View.OnClickListener{
    private EditText nomEditText;
    private EditText categorieEditText;
    private EditText provenanceEditText;
    private EditText commentaireEditText;
    private Button validerButton;
    private Button annulerButton;

    private OnButtonClickedListener mCallback;

    public interface OnButtonClickedListener {
        void onButtonClicked(View view);
    }

    private OnDataPass dataPass;

    public interface OnDataPass{
        void onDataPass(Depense d);
    }

    public DepenseFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_depense, container, false);
        validerButton = result.findViewById(R.id.ajout_depense_valide_button);
        annulerButton = result.findViewById(R.id.ajout_depense_annuler_button);
        nomEditText = result.findViewById(R.id.ajout_depense_nom_edit_text);
        categorieEditText = result.findViewById(R.id.ajout_depense_catégorie_edit_text);
        provenanceEditText = result.findViewById(R.id.ajout_depense_provenance_edit_text);
        commentaireEditText = result.findViewById(R.id.ajout_depense_commentaire_edit_text);
        result.findViewById(R.id.ajout_depense_annuler_button).setOnClickListener(this);
        result.findViewById(R.id.ajout_depense_valide_button).setOnClickListener(this);
        return result;
    }

    @Override
    public void onClick(View view) {
        Log.d("DEBUG",view.toString());
        Log.d("DEBUG",view.getId()+"");
        mCallback.onButtonClicked(view);
        if(view.getId() == validerButton.getId())
            dataPass.onDataPass(new Depense("",2,"",0,"",""));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        createCallbackToParentActivity();
        dataPass = (OnDataPass) context;
    }

    private void createCallbackToParentActivity(){
        try {
            //Parent activity will automatically subscribe to callback
            mCallback = (OnButtonClickedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e+ " must implement OnButtonClickedListener");
        }
    }


}