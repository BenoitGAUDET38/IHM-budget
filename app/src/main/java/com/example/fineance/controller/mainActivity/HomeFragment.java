package com.example.fineance.controller.mainActivity;

import static com.example.fineance.model.PerformNetworkRequest.getDepenses;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.fineance.R;
import com.example.fineance.controller.optionsActivity.SettingsActivity;
import com.example.fineance.controller.spendingActivity.AddExpenseActivity;
import com.example.fineance.model.Adapter.DepenseListAdapter;
import com.example.fineance.model.Depense;
import com.example.fineance.model.DepenseUtilities;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private List<Depense> depenseList;
    private final String devise = "€";
    private double totalDepenses = 0;
    ListView listView;

    public HomeFragment() {
        depenseList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDepenses();
        this.requireActivity().getWindow().setStatusBarColor(ContextCompat.getColor(this.requireActivity(), R.color.primary_blue));
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.requireActivity().getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            this.requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View moneyCircle = view.findViewById(R.id.moneyCircleButton);
        moneyCircle.setOnClickListener(v -> this.startActivity(new Intent(this.getActivity(), AddExpenseActivity.class)));

        ImageView account = view.findViewById(R.id.accountOptionImageView);
        account.setOnClickListener(v -> this.startActivity(new Intent(getActivity(), SettingsActivity.class)));

        listView = view.findViewById(R.id.recent_transactions);
        listView.setAdapter(new DepenseListAdapter(getActivity(), depenseList, true));
    }

    public void updateTotal(List<Depense> depenseArrayList) {
        if (this.getActivity() != null) {
            totalDepenses = DepenseUtilities.getMontantTotal(depenseArrayList);
            TextView montant = this.requireActivity().findViewById(R.id.info_total);
            montant.setText(totalDepenses +" "+ devise);
            depenseList = depenseArrayList;
            listView.setAdapter(new DepenseListAdapter(getActivity(),depenseArrayList,true));
        }
    }
}