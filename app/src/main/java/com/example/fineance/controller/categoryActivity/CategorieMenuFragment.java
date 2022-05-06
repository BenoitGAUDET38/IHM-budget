package com.example.fineance.controller.categoryActivity;

import static com.example.fineance.model.PerformNetworkRequest.getCategories;
import static com.example.fineance.model.PerformNetworkRequest.getDepenses;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.example.fineance.R;
import com.example.fineance.controller.ListFragment.DepenseListFragment;
import com.example.fineance.model.Adapter.CategorieListAdapter;
import com.example.fineance.model.Adapter.DepenseListAdapter;
import com.example.fineance.model.Categorie;
import com.example.fineance.model.Depense;
import com.example.fineance.model.DepenseUtilities;

import java.util.ArrayList;
import java.util.List;

public class CategorieMenuFragment extends Fragment {

    private List<Depense> depenseList = new ArrayList<>();
    private List<Categorie> categorieList = new ArrayList<>();
    private SwitchCompat categoriesOn;

    public CategorieMenuFragment() {
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
                listView.setAdapter(new CategorieListAdapter(getActivity(), categorieList));
            else
                listView.setAdapter(new DepenseListAdapter(getActivity(), depenseList));
        });
        montant.setText(String.valueOf(DepenseUtilities.getMontantTotal(depenseList)));
        ImageView addButon = view.findViewById(R.id.addButton);
        addButon.setOnClickListener(v -> {
            if(categoriesOn.isChecked())
                this.startActivity(new Intent(this.getActivity(), AddCategoryActivity.class));
            else
                getParentFragmentManager().beginTransaction().replace(R.id.list_content, new DepenseListFragment()).commit();
//                this.startActivity(new Intent(this.getActivity(), AddExpenseActivity.class));
        });
        return view;
    }

    public void updateList(List depenses) {
        TextView montant;
        Log.d("OBS", " " + depenses.get(0).getClass());
        if (getActivity() != null) {
            ListView listView = getActivity().findViewById(R.id.categorieListView);
            if(depenses.get(0).getClass().equals(Depense.class)){
                depenseList = (List<Depense>) depenses;
                listView.setAdapter(new DepenseListAdapter(getActivity(), depenseList));
                montant = getActivity().findViewById(R.id.ajout_depense_montant_editText);
                montant.setText(String.valueOf(DepenseUtilities.getMontantTotal(depenseList)));
            }else if(depenses.get(0).getClass().equals(Categorie.class)){
                categorieList = (List<Categorie>) depenses;
                listView.setAdapter(new CategorieListAdapter(getActivity(), categorieList));
            }
        }
    }

//    public void updateList(List<Categorie> categories) {
//        if (!isNull(getActivity())) {
//            ListView listView = getActivity().findViewById(R.id.categorieListView);
//            listView.setAdapter(new DepenseListAdapter(getActivity(), depenseList));
//        }
//        categorieList = categories;
//        Log.d("FRAGMENT", "Update " + categories);
//    }
}