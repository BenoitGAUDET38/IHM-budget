package com.example.fineance.controller.categoryActivity;

import static com.example.fineance.model.PerformNetworkRequest.categoriesList;
import static com.example.fineance.model.PerformNetworkRequest.getDepenses;
import static java.util.Objects.isNull;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.example.fineance.R;
import com.example.fineance.model.Adapter.CategorieListAdapter;
import com.example.fineance.model.Adapter.DepenseListAdapter;
import com.example.fineance.model.Depense;
import com.example.fineance.model.DepenseUtilities;

import java.util.ArrayList;
import java.util.List;

public class CategorieFragment extends Fragment {

    private List<Depense> depenseList = new ArrayList<>();
    private List<Depense> categorieList = new ArrayList<>();
    private SwitchCompat categoriesOn;

    public CategorieFragment() {
        getDepenses();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        //updateList(depenseList);
        getDepenses();
        Log.d("FRAGMENT", "onCreate: ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        getDepenses();
        Log.d("FRAGMENT", "onCreateView");
        Log.d("FRAGMENT", String.valueOf(depenseList));
        View view = inflater.inflate(R.layout.fragment_categorie, container, false);
        ListView listView = view.findViewById(R.id.categorieListView);
        listView.setAdapter(new DepenseListAdapter(getActivity(), depenseList));

        TextView montant = view.findViewById(R.id.ajout_depense_montant_editText);
        categoriesOn = view.findViewById(R.id.categorieSwitch);
        categoriesOn.setOnClickListener(viewSwitch -> {
            if (categoriesOn.isChecked())
                listView.setAdapter(new CategorieListAdapter(getActivity(), categoriesList));
            else
                listView.setAdapter(new DepenseListAdapter(getActivity(), depenseList));
        });
        montant.setText(DepenseUtilities.getMontantTotal(depenseList) + "");
        return view;
    }

    public void updateList(List<Depense> depenses) {
        TextView montant;
        if (!isNull(getActivity())) {
            ListView listView = getActivity().findViewById(R.id.categorieListView);
            listView.setAdapter(new DepenseListAdapter(getActivity(), depenseList));

            montant = getActivity().findViewById(R.id.ajout_depense_montant_editText);
            montant.setText(DepenseUtilities.getMontantTotal(depenses) + "");
        }
        depenseList = depenses;
        Log.d("FRAGMENT", "Update " + depenses);
    }
}