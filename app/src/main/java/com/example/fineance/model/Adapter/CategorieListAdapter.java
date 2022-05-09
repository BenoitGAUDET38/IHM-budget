package com.example.fineance.model.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fineance.R;
import com.example.fineance.model.Categorie;

import java.util.List;

public class CategorieListAdapter extends BaseAdapter {

    private final List<Categorie> listData;
    private final LayoutInflater layoutInflater;
    private boolean shorten = false;

    public CategorieListAdapter(Context aContext, List<Categorie> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    public CategorieListAdapter(Context aContext, List<Categorie> listData, boolean shorten) {
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

    @SuppressLint("SetTextI18n")
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_layout, null);
            holder = new ViewHolder();
            holder.nomView = convertView.findViewById(R.id.ajout_categorie_nom_edit_text);
            holder.seuilView = convertView.findViewById(R.id.ajout_categorie_seuil_edit_text);
            holder.commentaireView = convertView.findViewById(R.id.ajout_categorie_commentaire_edit_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Categorie categorie = this.listData.get(position);
        holder.nomView.setText(categorie.getNom());
        holder.seuilView.setText(categorie.getSeuil() + "$");
        holder.commentaireView.setText(categorie.getNom() + " " + categorie.getCommentaire());

        return convertView;
    }

    private static class ViewHolder {
        TextView nomView;
        TextView seuilView;
        TextView commentaireView;
    }
}
