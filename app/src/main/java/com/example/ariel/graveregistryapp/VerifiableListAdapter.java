package com.example.ariel.graveregistryapp;

import android.content.Context;
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

    // The activity that will use the list adapter
    public VerifiableActivity activity;

    /**
     * Constructor for VerifiableListAdapter
     * @param va the verifiable activity that will use the adapter
     */
    VerifiableListAdapter(VerifiableActivity va) {
        this.activity = va;
    }

    /**
     * Gets the number of entries
     * @return the number of entries
     */
    @Override
    public int getCount() {
        return activity.entries.size();
    }

    /**
     * Gets the item
     * @param i the count
     * @return the item
     */
    @Override
    public Object getItem(int i) {
        return null;
    }

    /**
     * Gets the itemID
     * @param i the count
     * @return the item ID
     */
    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     * A static ViewHolderItem class with name, cemetery, and conflict
     */
    static class ViewHolderItem {
        TextView name;
        TextView cemetery;
        TextView conflict;
    }

    /**
     * The getView method for the adapter
     * @param i the count
     * @param convertView the view
     * @param parent the parent
     * @return  the view
     */
    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        ViewHolderItem holder = new ViewHolderItem();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cell, null);

            holder.name = convertView.findViewById(R.id.name);
            holder.cemetery = convertView.findViewById(R.id.cemetery);
            holder.conflict = convertView.findViewById(R.id.conflict);

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
