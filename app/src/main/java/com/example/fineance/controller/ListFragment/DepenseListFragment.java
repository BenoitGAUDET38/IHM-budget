package com.example.fineance.controller.ListFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.fineance.R;
import com.example.fineance.model.Depense;

import java.util.ArrayList;
import java.util.List;


public class DepenseListFragment extends Fragment {

   private List<Depense> depenses= new ArrayList<>();

    public DepenseListFragment(List<Depense> depenses) {
        this.depenses = depenses;
    }

    public DepenseListFragment() {
    }

    public static DepenseListFragment newInstance(String param1, String param2) {
        DepenseListFragment fragment = new DepenseListFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("DEBUG","Hey you");
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_depense_list, container, false);
    }
}