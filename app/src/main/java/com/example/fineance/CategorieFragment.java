package com.example.fineance;

import static com.example.fineance.modeles.PerformNetworkRequest.getCategories;
import static com.example.fineance.modeles.PerformNetworkRequest.getDepenses;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.fineance.categories.CategorieListAdapter;
import com.example.fineance.depenses.DepenseListAdapter;
import com.example.fineance.categories.AddCategoryActivity;
import com.example.fineance.depenses.AddExpenseActivity;
import com.example.fineance.depenses.DepenseListFragment;
import com.example.fineance.modeles.Categorie;
import com.example.fineance.modeles.Depense;
import com.example.fineance.modeles.DepenseUtilities;

import java.util.ArrayList;
import java.util.List;

public class CategorieFragment extends Fragment {

    private ArrayList<Depense> depenseList = new ArrayList<>();
    private List<Categorie> categorieList = new ArrayList<>();
    private SwitchCompat categoriesOn;
    private ListView listView;

    public CategorieFragment() {
        getDepenses();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        getCategories();
        getDepenses();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.requireActivity().getWindow().setStatusBarColor(ContextCompat.getColor(this.requireActivity(), R.color.primary_red));
        View view = inflater.inflate(R.layout.fragment_categorie, container, false);

        listView = view.findViewById(R.id.categorieListView);
        TextView montant = view.findViewById(R.id.ajout_depense_montant_editText);
        categoriesOn = view.findViewById(R.id.categorieSwitch);
        categoriesOn.setOnClickListener(viewSwitch -> {
            if (categoriesOn.isChecked())
                setCategories();
            else
                setDepenses();
        });
        montant.setText(String.valueOf(DepenseUtilities.getMontantTotal(depenseList)));
        ImageView addButon = view.findViewById(R.id.addButton);
        addButon.setOnClickListener(v -> {
            if (categoriesOn.isChecked())
                this.startActivity(new Intent(this.getActivity(), AddCategoryActivity.class));
            else
                this.startActivity(new Intent(this.getActivity(), AddExpenseActivity.class));
        });
        return view;
    }

    private void setCategories() {
        listView.setAdapter(new CategorieListAdapter(getActivity(), categorieList));
        if (getParentFragmentManager().findFragmentById(R.id.list_content) != null)
            getParentFragmentManager().beginTransaction().remove(getParentFragmentManager().findFragmentById(R.id.list_content)).commit();
    }

    private void setDepenses() {
        listView.setAdapter(new DepenseListAdapter(getActivity(), new ArrayList<>()));
        DepenseListFragment f = DepenseListFragment.newDepenseList(depenseList);
        getParentFragmentManager().beginTransaction().replace(R.id.list_content, f).commit();
    }

    public void updateList(List depenses) {
        TextView montant;
        if (getActivity() != null) {
            if (depenses.get(0).getClass().equals(Depense.class)) {
                depenseList = (ArrayList<Depense>) depenses;
                setDepenses();
                montant = getActivity().findViewById(R.id.ajout_depense_montant_editText);
                montant.setText(String.valueOf(DepenseUtilities.getMontantTotal(depenseList)));
            } else if (depenses.get(0).getClass().equals(Categorie.class)) {
                categorieList = (List<Categorie>) depenses;
                setCategories();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        categoriesOn.setChecked(false);
        setDepenses();
    }
}