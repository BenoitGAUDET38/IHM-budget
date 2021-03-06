package com.example.fineance.depenses;

import static com.example.fineance.modeles.PerformNetworkRequest.findCategorieById;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fineance.R;
import com.example.fineance.modeles.Categorie;
import com.example.fineance.modeles.Depense;

import java.util.List;

public class DepenseListAdapter extends BaseAdapter {

    private final List<Depense> listData;
    private final LayoutInflater layoutInflater;
    private boolean shorten = false;

    public DepenseListAdapter(Context aContext, List<Depense> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
        listData.sort((depense, t1) -> t1.getDate().compareTo(depense.getDate()));
    }

    public DepenseListAdapter(Context aContext, List<Depense> listData, boolean shorten) {
        this(aContext, listData);
        this.shorten = shorten;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"SetTextI18n", "InflateParams"})
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.depense_item_layout, null);
            holder = new ViewHolder();
            holder.categorieNameView = view.findViewById(R.id.item_layout_textView_categorie);
            holder.provenanceView = view.findViewById(R.id.item_layout_textView_provenance);
            holder.nomEtPrixView = view.findViewById(R.id.item_layout_textView_nomprix);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Depense depense = this.listData.get(position);
        Categorie c = findCategorieById(depense.getCategorie());
        if (!this.shorten) holder.categorieNameView.setText(
                (c !=null ? c.getNom():"")
        );
        if (!this.shorten)  holder.provenanceView.setText(depense.getProvenance());
        holder.nomEtPrixView.setText(depense.getNom() + "    " + depense.getMontant() + " " + depense.getDevise());
        view.setOnClickListener(e -> {
            Intent in = new Intent(e.getContext(), AddExpenseActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("depense",depense);
            in.putExtras(bundle);
            e.getContext().startActivity(in);
        });
        return view;
    }

    private static class ViewHolder {
        TextView categorieNameView;
        TextView provenanceView;
        TextView nomEtPrixView;
    }
}
