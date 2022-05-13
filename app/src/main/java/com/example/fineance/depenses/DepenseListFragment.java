package com.example.fineance.depenses;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.fineance.R;
import com.example.fineance.modeles.Depense;

import java.util.ArrayList;
import java.util.List;


public class DepenseListFragment extends Fragment {

   private List<Depense> depenses= new ArrayList<>();

    public DepenseListFragment() {
    }

    public static DepenseListFragment newDepenseList(List<Depense> depenseList) {
        DepenseListFragment fragment = new DepenseListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("depenses", (ArrayList<? extends Parcelable>) depenseList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            depenses = (List<Depense>) getArguments().get("depenses");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_depense_list, container, false);
        ListView listView = view.findViewById(R.id.list_depenses);
        listView.setAdapter(new DepenseListAdapter(getContext(), depenses));
        return view;
    }
}