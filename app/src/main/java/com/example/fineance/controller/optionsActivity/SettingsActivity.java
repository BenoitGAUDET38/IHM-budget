package com.example.fineance.controller.optionsActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fineance.R;
import com.example.fineance.controller.categoryActivity.AddCategoryActivity;
import com.example.fineance.controller.spendingActivity.AddExpenseActivity;
import com.example.fineance.model.Adapter.OptionListAdapter;
import com.example.fineance.model.settings.Option;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private static List<Option> optionList = new ArrayList<>();
    public TextView log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_settings);

        Button addCategory = new Button(this);
        Button addExpense = new Button(this);
        addCategory.setId(R.id.option_item_value_textView);
        addExpense.setId(R.id.option_item_value_textView);
        log = findViewById(R.id.last_log);
        // TODO ICI ça crash quand clické dessus.
        addCategory.setOnClickListener(v -> this.startActivity(new Intent(this, AddCategoryActivity.class)));
        addExpense.setOnClickListener(v -> this.startActivity(new Intent(this, AddExpenseActivity.class)));
        optionList = new ArrayList<>();
        optionList.add(new Option("Ajout Catégorie", addCategory));
        optionList.add(new Option("Ajout Dépense", addExpense));

        ListView optionListView = this.findViewById(R.id.optionListView);
        optionListView.setAdapter(new OptionListAdapter(this, optionList));
    }

    public void showLogs(View view) {
        try {
            InputStream fis = openFileInput("Test");
            BufferedReader r = new BufferedReader(new InputStreamReader(fis));

            String line;
            StringBuilder res = new StringBuilder();
            while ((line = r.readLine()) != null) {
                res.append(line).append("\n");
            }
            log.setText(res.toString());
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}