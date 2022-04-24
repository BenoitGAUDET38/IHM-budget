package com.example.fineance.model.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fineance.R;
import com.example.fineance.model.Depense;

import java.util.List;

public class DepenseListAdapter extends BaseAdapter {

    private final List<Depense> listData;
    private final LayoutInflater layoutInflater;

    public DepenseListAdapter(Context aContext, List<Depense> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
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

    @SuppressLint("SetTextI18n")
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_item_layout, null);
            holder = new ViewHolder();
            holder.categorieNameView = view.findViewById(R.id.item_layout_textView_categorie);
            holder.provenanceView = view.findViewById(R.id.item_layout_textView_provenance);
            holder.nomEtPrixView = view.findViewById(R.id.item_layout_textView_nomprix);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Depense depense = this.listData.get(position);
        holder.categorieNameView.setText(depense.getCategorie());
        holder.provenanceView.setText(depense.getProvenance());
        holder.nomEtPrixView.setText(depense.getNom() + "    " + depense.getMontant() + "$");

        return view;
    }

    private static class ViewHolder {
        TextView categorieNameView;
        TextView provenanceView;
        TextView nomEtPrixView;
    }
}
