package com.example.fineance.model.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fineance.R;
import com.example.fineance.controller.categoryActivity.DisplayCategorieActivity;
import com.example.fineance.model.Categorie;

import java.util.List;

public class CategorieListAdapter extends BaseAdapter {

    private final List<Categorie> listData;
    private final LayoutInflater layoutInflater;

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

    @SuppressLint("SetTextI18n")
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.categorie_item_layout, null);
            holder = new ViewHolder();
            holder.nomView = convertView.findViewById(R.id.categorie_name);
            holder.seuilView = convertView.findViewById(R.id.categorie_seuil);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Categorie categorie = this.listData.get(position);
        holder.nomView.setText(categorie.getNom());
        holder.seuilView.setText(categorie.getSeuil() + "$");
        convertView.setOnLongClickListener(e -> {
            Intent in = new Intent(e.getContext(), DisplayCategorieActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("categorie",categorie);
            Log.d("DEBUG","Intent "+categorie);
            in.putExtras(bundle);
            e.getContext().startActivity(in);
            return true;
        });
        return convertView;
    }

    private static class ViewHolder {
        TextView nomView;
        TextView seuilView;
    }
}
