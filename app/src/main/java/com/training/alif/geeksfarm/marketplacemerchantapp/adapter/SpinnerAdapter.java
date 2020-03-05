package com.training.alif.geeksfarm.marketplacemerchantapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.training.alif.geeksfarm.marketplacemerchantapp.R;
import com.training.alif.geeksfarm.marketplacemerchantapp.entity.Category;

import java.util.ArrayList;

public class SpinnerAdapter extends BaseAdapter {
    ArrayList<Category> categories = new ArrayList<>();

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Category getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return categories.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.categor_dropdown_spinner, parent,false);
        }
        Category category = getItem(position);
        TextView tvText = convertView.findViewById(R.id.cat_text);
        tvText.setText(category.getName());

        return convertView;
    }

    public void addData(ArrayList<Category> categories){
        this.categories = categories;
    }
}
