package com.example.fineance.controller.categoryActivity;

import static com.example.fineance.model.PerformNetworkRequest.depenseList;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.fineance.R;
import com.example.fineance.model.Adapter.DepenseListAdapter;
import com.example.fineance.model.Depense;
import com.example.fineance.model.PerformNetworkRequest;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategorieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategorieFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public CategorieFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategorieFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategorieFragment newInstance(String param1, String param2) {
        CategorieFragment fragment = new CategorieFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categorie, container, false);
        ListView listView = view.findViewById(R.id.categorieListView);
        PerformNetworkRequest.getDepenses();
        listView.setAdapter(new DepenseListAdapter(getActivity(), depenseList));

        TextView montant = view.findViewById(R.id.ajout_depense_montant_editText);
        montant.setText(depenseList.stream().map(Depense::getMontant).reduce(0.0, Double::sum).toString());

        return view;
    }
}