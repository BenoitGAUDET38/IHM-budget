package com.example.fineance.controller.previsionActivity;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.fineance.R;
import com.example.fineance.model.Depense;
import com.example.fineance.model.DepenseUtilities;
import com.example.fineance.model.PerformNetworkRequest;
import com.example.fineance.model.notifications.Notification;
import com.example.fineance.model.notifications.notificationsFactories.AbstractNotificationFactory;
import com.example.fineance.model.notifications.notificationsFactories.LowPriorityNotificationFactory;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrevisionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrevisionFragment extends Fragment {
    GraphView graphView;
    AnimatedPieView pieChart;
    Spinner spinnerMois;
    Spinner spinnerAnnee;
    String[] mois = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    String[] annee = new String[]{"2022", "2021", "2020", "2019", "2018", "2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010"};
    int moisActuel = 4;
    int anneeActuel = 2022;
    String currentDuration = "Annuel";
    int spinnerPosition = 1;
    Button btn_swap;
    int mode = 0;

    public PrevisionFragment() {
        // Required empty public constructor
    }

    public PrevisionFragment(int mode) {
        this.mode = mode;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PrevisionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PrevisionFragment newInstance(String param1, String param2) {
        PrevisionFragment fragment = new PrevisionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.requireActivity().getWindow().setStatusBarColor(ContextCompat.getColor(this.requireActivity(), R.color.primary_green));
        View view = inflater.inflate(R.layout.fragment_prevision, container, false);
        if (mode != 0)
            view = inflater.inflate(R.layout.fragment_prevision_big_pie, container, false);


        graphView = view.findViewById(R.id.graphView);
        pieChart = view.findViewById(R.id.pie_chart);
        spinnerMois = view.findViewById(R.id.spinner_prevision_mois);
        spinnerAnnee = view.findViewById(R.id.spinner_prevision_année);
        btn_swap = view.findViewById(R.id.btn_swap);

        setupSwapBtn();

        setupSpinner(spinnerMois, mois);
        setupSpinner(spinnerAnnee, annee);
        spinnerMois.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Adapter adapter = adapterView.getAdapter();
                if (adapter.getItem(position) != null) {
                    setMoisActuel(Integer.parseInt((String) adapter.getItem(position)));
                    drawLineGraph();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerAnnee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Adapter adapter = adapterView.getAdapter();
                if (adapter.getItem(position) != null) {
                    setAnneeActuel(Integer.parseInt((String) adapter.getItem(position)));
                    drawLineGraph();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        drawLineGraph();
        drawPieChart();

        return view;
    }

    void setupSwapBtn() {
        btn_swap.setOnClickListener(view -> {
            int argMode = 0;
            if (mode == 0) argMode = 1;
            this.requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new PrevisionFragment(argMode)).commit();
            AbstractNotificationFactory factory = new LowPriorityNotificationFactory();
            Notification notification;
            if (argMode != 0)
                notification = factory.buildImageNotification(getActivity(), getResources(), AbstractNotificationFactory.ALARME_IMG, "Changement éléments", "Le graphique camembert est maintenant mieux visible");
            else
                notification = factory.buildBasicNotification(getActivity(), "Changement éléments", "Les éléments sont de retour à l'affichage par défaut de la page");
            notification.sendNotificationOnChannel();
        });
    }

    void setupSpinner(Spinner spinner, String[] values) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, values);
        spinner.setAdapter(adapter);
        spinner.setSelection(spinnerPosition);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                spinnerPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    void drawPieChart() {
        String[] rgb = {"#07108c", "#661600", "#348f94", "BB0306", "#308958", "#6E125C"};
        Map<String, Double> mapInfo = DepenseUtilities.getDepenseParCategorie(PerformNetworkRequest.getDepenses());

        Random rnd = new Random();

        AnimatedPieViewConfig config = new AnimatedPieViewConfig();
        config.duration(2000)
                .startAngle(-90f)
                .drawText(true)
                .textSize(40)
                .canTouch(true)
                .focusAlpha(150)
                .selectListener((pieInfo, isFloatUp) -> Toast.makeText(getActivity(), pieInfo.getDesc(), Toast.LENGTH_SHORT).show());

        int i = 0;
        for (Map.Entry<String, Double> entry : mapInfo.entrySet()) {
            int color;
            if (i < rgb.length) {
                color = Color.parseColor(rgb[i]);
            } else {
                color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            }
            config.addData(new SimplePieInfo(entry.getValue(),
                    color,
                    entry.getKey() + " : " + entry.getValue() + "€"));
            i++;
        }

        pieChart.applyConfig(config);
        pieChart.start();
    }

    void drawLineGraph() {
        Timestamp start = new Timestamp(anneeActuel - 1900, moisActuel - 1, 1, 0, 0, 0, 0);
        Timestamp end = new Timestamp(anneeActuel - 1900, moisActuel - 1, 31, 0, 0, 0, 0);
        List<Depense> depenseList = DepenseUtilities.getDepenseParDuree(PerformNetworkRequest.getDepenses(), start, end);
        System.out.println(depenseList);
        List<DataPoint> dataPointList = new ArrayList<>();
        double montant = 0;
        dataPointList.add(new DataPoint(0, 0));
        for (Depense depense : depenseList) {
            montant += depense.getMontant();
            dataPointList.add(new DataPoint(depense.getDate().getDate(), montant));
        }
        if (depenseList.size() == 0)
            dataPointList.add(new DataPoint(31, 0));

        // on below line we are adding data to our graph view.
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPointList.toArray(new DataPoint[0]));


        // set manual X bounds
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(0);
        graphView.getViewport().setMaxY(series.getHighestValueY());

        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setMaxX(series.getHighestValueX());

        // enable scaling and scrolling
        graphView.getViewport().setScalable(true);
        graphView.getViewport().setScalableY(true);
        graphView.addSeries(series);
        System.out.println("bla");
    }

    public void setMoisActuel(int moisActuel) {
        this.moisActuel = moisActuel;
    }

    public void setAnneeActuel(int anneeActuel) {
        this.anneeActuel = anneeActuel;
    }

    public int getMoisActuel() {
        return moisActuel;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("mois", moisActuel);
        outState.putInt("annee", anneeActuel);
        super.onSaveInstanceState(outState);
    }

}