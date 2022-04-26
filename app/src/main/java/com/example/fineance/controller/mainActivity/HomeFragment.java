package com.example.fineance.controller.mainActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
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

    private double totalDepenses = 0;
    private String devise = "€";

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.totalDepenses = getArguments().getDouble(TOTAL_KEY, 0);
            this.devise = getArguments().getString(DEVISE_KEY, "€");
        }
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
        setupInfos();
    }

    private void setupInfos() {
        TextView infos = this.requireView().findViewById(R.id.info_total);
        infos.setText(totalDepenses + devise);
    }
}