package com.example.fineance.controller.mainActivity;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.fineance.R;
import com.example.fineance.model.notifications.Notification;
import com.example.fineance.model.notifications.notificationsFactories.AbstractNotificationFactory;
import com.example.fineance.model.notifications.notificationsFactories.LowPriorityNotificationFactory;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrevisionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrevisionFragment extends Fragment {
    GraphView graphView;
    AnimatedPieView pieChart;
    Spinner spinner;
    String[] duration = new String[]{"Hebdomadaire", "Mensuel", "Annuel"};
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_prevision, container, false);
        if (mode != 0)
            view = inflater.inflate(R.layout.fragment_prevision_big_pie, container, false);


        graphView = view.findViewById(R.id.graphView);
        pieChart = view.findViewById(R.id.pie_chart);
        spinner = view.findViewById(R.id.spinner_prevision);
        btn_swap = view.findViewById(R.id.btn_swap);

        setupSwapBtn();

        setupSpinner();
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

    void setupSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, duration);
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
        int sizeList = 3;
        String[] cat = {"Course", "Sport", "Liquide"};
        double[] value = {304.91, 267.56, 25.01};
        String[] rgb = {"#07108c", "#661600", "#348f94"};

        Random rnd = new Random();

        AnimatedPieViewConfig config = new AnimatedPieViewConfig();
        config.duration(2000)
                .startAngle(-90f)
                .drawText(true)
                .textSize(50)
                .canTouch(true)
                .focusAlpha(150)
                .selectListener((pieInfo, isFloatUp) -> Toast.makeText(getActivity(), pieInfo.getDesc(), Toast.LENGTH_SHORT).show());


        // injection des données
        for (int i = 0; i < sizeList; i++) {
            config.addData(new SimplePieInfo(value[i],
                    //Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)),
                    Color.parseColor(rgb[i]),
                    cat[i] + " : " + value[i] + "€"));
        }

        pieChart.applyConfig(config);
        pieChart.start();
    }

    void drawLineGraph() {
        // on below line we are adding data to our graph view.
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                // on below line we are adding
                // each point on our x and y axis.
                new DataPoint(1, 12.68),
                new DataPoint(2, 56.4),
                new DataPoint(3, 681.2),
                new DataPoint(4, 681.2),
                new DataPoint(5, 900.00),
                new DataPoint(6, 960.00),
        });
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
    }
}