package com.example.ariel.graveregistryapp;

/**
 * Created by Holly Calloway on 2/28/2018.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class GravesAdapter extends RecyclerView.Adapter<GravesAdapter.MyViewHolder>{

    private List<Graves> gravesList;


    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView firstName, lastName, conflict, cemetery;

        public MyViewHolder(View view) {
            super(view);
            firstName = view.findViewById(R.id.et_firstName);
            lastName = view.findViewById(R.id.et_lastName);
            conflict = view.findViewById(R.id.et_conflict);
            cemetery = view.findViewById(R.id.et_cemetery);
        }
    }

    public GravesAdapter(List<Graves> gravesList)
    {
        this.gravesList = gravesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_verifiable, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        Graves grave = gravesList.get(position);
        holder.firstName.setText(grave.getFirstName());
        holder.lastName.setText(grave.getLastName());
        holder.conflict.setText(grave.getConflict());
        holder.cemetery.setText(grave.getCemetery());
    }

    @Override
    public int getItemCount()
    {
        return gravesList.size();
    }


}
