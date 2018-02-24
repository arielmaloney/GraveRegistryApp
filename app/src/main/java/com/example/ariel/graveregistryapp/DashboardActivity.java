package com.example.ariel.graveregistryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        final TextView welcomeMsg = findViewById(R.id.tvWelcomeMessage);

        final Button graveRegButton = findViewById(R.id.graveRegButton);
        final Button logoutButton = findViewById(R.id.logoutButton);


        graveRegButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // On Grave Registry button click will transfer User to Grave Registry Activity
                Intent iGraveReg = new Intent(DashboardActivity.this, GraveRegistryActivity.class);
                startActivity(iGraveReg);
            }

        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // On Logout button click will Logout User and transfer back to Login page
                Intent iLogin = new Intent(DashboardActivity.this, LoginActivity.class);
                startActivity(iLogin);
            }
        });
    }

}
