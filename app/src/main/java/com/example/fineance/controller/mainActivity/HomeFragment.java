package com.example.fineance.controller.mainActivity;

import static com.example.fineance.model.PerformNetworkRequest.getDepenses;
import static java.util.Objects.isNull;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fineance.R;
import com.example.fineance.controller.optionsActivity.SettingsActivity;
import com.example.fineance.controller.spendingActivity.AddExpenseActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    public static String TOTAL_KEY = "total";
    public static String DEVISE_KEY = "devise";

    private double totalDepenses=0;
    private String devise = "€";

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        TextView montant = view.findViewById(R.id.info_total);
        montant.setText(totalDepenses + this.devise);
        Log.d("DEBUG","Create");
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        getDepenses();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView moneyCircle = this.requireView().findViewById(R.id.moneyCircleButton);
        moneyCircle.setOnClickListener(v -> {
            this.startActivity(new Intent(this.getActivity(), AddExpenseActivity.class));
        });
        ImageView account = this.requireView().findViewById(R.id.accountOptionImageView);
        account.setOnClickListener(v -> {
            this.startActivity(new Intent(getActivity(), SettingsActivity.class));
        });
    }

    public void updateTotal(double total){
        if(!isNull(getActivity())){
            TextView montant = getActivity().findViewById(R.id.info_total);
            totalDepenses = total;
            montant.setText(totalDepenses+devise);
        }
    }
}