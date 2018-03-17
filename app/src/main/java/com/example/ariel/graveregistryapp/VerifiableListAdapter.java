package com.example.ariel.graveregistryapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * List View Adpater to display custom view in list
 * Created by Ariel on 3/17/2018.
 */

public class VerifiableListAdapter extends BaseAdapter {

    public VerifiableActivity activity;

    VerifiableListAdapter(VerifiableActivity va) {
        this.activity = va;
    }

    @Override
    public int getCount() {
        return activity.entries.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    static class ViewHolderItem {
        TextView name;
        TextView cemetery;
        TextView conflict;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        ViewHolderItem holder = new ViewHolderItem();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cell, null);

            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.cemetery = (TextView) convertView.findViewById(R.id.cemetery);
            holder.conflict = (TextView) convertView.findViewById(R.id.conflict);

            convertView.setTag(holder);

        }
        else {
            holder = (ViewHolderItem) convertView.getTag();
        }

        holder.name.setText(this.activity.entries.get(i).name);
        holder.cemetery.setText(this.activity.entries.get(i).cemetery);
        holder.conflict.setText(this.activity.entries.get(i).conflict);
        return convertView;
    }
}
