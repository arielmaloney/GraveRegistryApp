package com.example.ariel.graveregistryapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class VerifiableActivity extends AppCompatActivity {

    private List<Graves> gravesList = new ArrayList<>();
    GravesAdapter gravesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_grave);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        gravesAdapter = new GravesAdapter(gravesList);

        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(gravesAdapter);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        testGraveData();
    }

    private void testGraveData()
    {
        Graves grave = new Graves("Holly", "Calloway",
                "My Conflict", "The Cemetery");
        gravesList.add(grave);

        grave = new Graves("Trenton", "Calloway",
                "His Conflict", "The Cemetery 2");
        gravesList.add(grave);

        grave = new Graves("Ashley", "Cross",
                "Her Conflict", "The Cemetery 3");
        gravesList.add(grave);

        grave = new Graves("Dustin", "Cross",
                "His Conflict 2", "The Cemetery 4");
        gravesList.add(grave);

        gravesAdapter.notifyDataSetChanged();

    }
}
