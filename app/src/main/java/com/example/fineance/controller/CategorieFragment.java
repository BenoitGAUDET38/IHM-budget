package com.example.fineance.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.fineance.R;
import com.example.fineance.model.CustomListAdapter;
import com.example.fineance.model.Depense;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategorieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategorieFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList<Depense> depenseArrayList;

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
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categorie, container, false);
        ListView listView = view.findViewById(R.id.listView);
        depenseArrayList=new ArrayList<>();
        depenseArrayList.add(new Depense("Petit pain","Course","Boulangerie",1,"EUR","A acheter"));
        depenseArrayList.add(new Depense("Haricots","Course","Auchan",0.6,"EUR","A acheter"));
        depenseArrayList.add(new Depense("Big mac","Fast-Food","Macdo",8,"EUR","A acheter"));

        listView.setAdapter(new CustomListAdapter(getActivity(), depenseArrayList));

        return view;
    }
}