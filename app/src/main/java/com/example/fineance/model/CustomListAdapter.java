package com.example.fineance.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fineance.R;

import java.util.List;

public class CustomListAdapter  extends BaseAdapter {

    private List<Depense> listData;
    private LayoutInflater layoutInflater;

    public CustomListAdapter(Context aContext,  List<Depense> listData) {
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

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_layout, null);
            holder = new ViewHolder();
            holder.categorieNameView = convertView.findViewById(R.id.item_layout_textView_categorie);
            holder.provenanceView = convertView.findViewById(R.id.item_layout_textView_provenance);
            holder.nomEtPrixView = convertView.findViewById(R.id.item_layout_textView_nomprix);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Depense depense = this.listData.get(position);
        holder.categorieNameView.setText(depense.getCategorie());
        holder.provenanceView.setText(depense.getProvenanace());
        holder.nomEtPrixView.setText(depense.getNom()+"    "+depense.getMontant()+"$");

        return convertView;
    }

    static class ViewHolder {
        TextView categorieNameView;
        TextView provenanceView;
        TextView nomEtPrixView;
    }

}
