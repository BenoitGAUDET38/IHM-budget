package com.example.fineance.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.fineance.R;
import com.example.fineance.categories.DisplayCategorieActivity;
import com.example.fineance.categories.DisplayDepenseActivity;
import com.example.fineance.modeles.Categorie;
import com.example.fineance.modeles.DepenseUtilities;
import com.example.fineance.modeles.PerformNetworkRequest;

import java.util.ArrayList;
import java.util.List;

public class CategorieListAdapter extends BaseAdapter {

    private final List<Categorie> listData;
    private final LayoutInflater layoutInflater;
    private final String devise = " EUR";

    public CategorieListAdapter(Context aContext, List<Categorie> listData) {
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

    @SuppressLint({"SetTextI18n"})
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.categorie_item_layout, null);
            holder = new ViewHolder();
            holder.nomView = convertView.findViewById(R.id.categorie_name);
            holder.seuilView = convertView.findViewById(R.id.categorie_seuil);
            holder.progressBarSeuil = convertView.findViewById(R.id.progress_seuil);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Categorie categorie = this.listData.get(position);
        double montant = DepenseUtilities.getMontantTotalParCategorie(PerformNetworkRequest.depenseList, categorie.getId());

        holder.nomView.setText(categorie.getNom());
        holder.seuilView.setText(montant + "/" + categorie.getSeuil() + " " + devise);

        holder.progressBarSeuil.setMax((int) categorie.getSeuil());
        holder.progressBarSeuil.setProgress((int) PerformNetworkRequest.sumCategorie(categorie.getId()));
        holder.progressBarSeuil.setProgressTintList(ColorStateList.valueOf(Color.parseColor(this.getColour(parent, montant / categorie.getSeuil()))));
        convertView.setOnLongClickListener(e -> {
            Intent in = new Intent(e.getContext(), DisplayCategorieActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("categorie", categorie);
            Log.d("DEBUG", "Intent " + categorie);
            in.putExtras(bundle);
            e.getContext().startActivity(in);
            return true;
        });
        convertView.setOnClickListener(e -> {
            Intent in = new Intent(e.getContext(), DisplayDepenseActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("depenses", (ArrayList<? extends Parcelable>) PerformNetworkRequest.depensesByCategorie(categorie.getId()));
            bundle.putString("categorie", categorie.getNom());
            in.putExtras(bundle);
            e.getContext().startActivity(in);
        });
        return convertView;
    }

    @SuppressLint("ResourceType")
    private String getColour(ViewGroup view, double percentage) {
        if (percentage <= 1 / 3.) return view.getResources().getString(R.color.secondary_green);
        if (percentage >= 2 / 3.) return view.getResources().getString(R.color.tertiary_red);
        return "#ffae00";
    }

    private static class ViewHolder {
        TextView nomView;
        TextView seuilView;
        ProgressBar progressBarSeuil;
    }
}
