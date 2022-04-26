package com.example.fineance.model.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fineance.R;
import com.example.fineance.model.settings.Option;

import java.util.List;

public class OptionListAdapter extends BaseAdapter {

    private final List<Option> listData;
    private final LayoutInflater layoutInflater;

    public OptionListAdapter(Context aContext, List<Option> listData) {
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

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.option_item_layout, null);
            holder = new ViewHolder();
            holder.nameView = view.findViewById(R.id.option_item_name_textView);
            holder.valueView = view.findViewById(R.id.option_item_value_textView);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Option option = this.listData.get(position);
        holder.nameView.setText(option.getName());
        holder.valueView = option.getView();
        holder.valueView.setOnClickListener(v -> {
            option.getView().callOnClick();
        });

        return view;
    }

    private static class ViewHolder {
        TextView nameView;
        View valueView;
    }
}
