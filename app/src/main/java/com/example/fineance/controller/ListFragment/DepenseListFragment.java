package com.example.fineance.controller.ListFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.fineance.R;
import com.example.fineance.model.Adapter.DepenseListAdapter;
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

    public static DepenseListFragment newDepenseList(ArrayList<Depense> depenseList) {
        DepenseListFragment fragment = new DepenseListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("depenses", depenseList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            depenses = (List<Depense>) getArguments().get("depenses");
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_depense_list, container, false);
        ListView listView = view.findViewById(R.id.list_depenses);
        Log.d("DEBUG","Hey you "+depenses+"\n"+getActivity());
        listView.setAdapter(new DepenseListAdapter(getContext(), depenses));
        return view;
    }
}