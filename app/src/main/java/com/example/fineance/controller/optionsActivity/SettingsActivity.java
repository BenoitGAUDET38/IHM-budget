package com.example.fineance.controller.optionsActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fineance.R;
import com.example.fineance.controller.categoryActivity.AddCategoryActivity;
import com.example.fineance.controller.spendingActivity.AddExpenseActivity;
import com.example.fineance.model.Adapter.OptionListAdapter;
import com.example.fineance.model.settings.Option;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private static final List<Option> optionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_settings);

        Button addCategory = new Button(this);
        Button addExpense = new Button(this);
        addCategory.setId(R.id.option_item_value_textView);
        addExpense.setId(R.id.option_item_value_textView);
        // TODO ICI ça crash quand clické dessus.
        addCategory.setOnClickListener(v -> this.startActivity(new Intent(this, AddCategoryActivity.class)));
        addExpense.setOnClickListener(v -> this.startActivity(new Intent(this, AddExpenseActivity.class)));
        optionList.add(new Option("Ajoute Catégorie", addCategory));
        optionList.add(new Option("Ajoute Dépense", addExpense));

        ListView optionListView = this.findViewById(R.id.optionListView);
        optionListView.setAdapter(new OptionListAdapter(this, optionList));
    }
}