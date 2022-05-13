package com.example.fineance;

import static com.example.fineance.modeles.PerformNetworkRequest.getDepenses;
import static com.example.fineance.modeles.User.holder;

import android.annotation.SuppressLint;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.fineance.modeles.Depense;
import com.example.fineance.modeles.DepenseUtilities;
import com.example.fineance.modeles.User;
import com.example.fineance.settings.SettingsActivity;
import com.example.fineance.depenses.AddExpenseActivity;
import com.example.fineance.depenses.DepenseListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private List<Depense> depenseList;
    private User u;
    private ListView listView;
    private TextView welcome;

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

        welcome = view.findViewById(R.id.welcome_message);
        listView = view.findViewById(R.id.recent_transactions);
        listView.setAdapter(new DepenseListAdapter(getActivity(), depenseList, true));

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void updateTotal(List<Depense> depenseArrayList) {
        if (this.getActivity() != null) {
            double totalDepenses = DepenseUtilities.getMontantTotal(depenseArrayList);
            TextView montant = this.requireActivity().findViewById(R.id.info_total);
            String devise = "€";
            String res = totalDepenses + " " + devise;
            montant.setText(res);
            depenseList = depenseArrayList;
            listView.setAdapter(new DepenseListAdapter(getActivity(), depenseArrayList, true));
        }
    }

    public void userUpdate() {
        if (u == null)
            u = new User();
        Log.d("USER", holder + "\n" + u);
        u.setUser(holder);
        String res = "Bienvenue " + u.getName();
        welcome.setText(res);
    }
}