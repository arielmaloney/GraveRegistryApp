package com.example.ariel.graveregistryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class DashboardActivity extends AppCompatActivity {

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        HashMap<String, String> userDetails = session.getUserDetails();
        String fn = userDetails.get(SessionManager.KEY_FIRSTNAME);
        String ln = userDetails.get(SessionManager.KEY_LASTNAME);

        // Sets the TextView to the users first and last name
        TextView welcomeMsg = findViewById(R.id.tvWelcomeMessage);
        TextView displayName = findViewById(R.id.firstnameTxt);
        displayName.setText(fn + " " + ln);

        final Button graveRegButton = findViewById(R.id.graveRegButton);
        final Button verifyGraveButton = findViewById(R.id.verifyGraveButton);
        final Button pastSubsButton = findViewById(R.id.viewPastSubButton);
        final Button logoutButton = findViewById(R.id.logoutButton);


        //Sets a OnClickListener to wait for the Grave Registry Button to be clicked and navigate the User
        graveRegButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // On Grave Registry button click will transfer User to Grave Registry Activity
                Intent iGraveReg = new Intent(DashboardActivity.this, GraveRegistryActivity.class);
                startActivity(iGraveReg);
            }

        });

        //Sets a OnClickListener to wait for the Verify Grave Button to be clicked and navigate the User
        verifyGraveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // On Grave Registry button click will transfer User to Verifiable Activity
                Intent iVerifyGrave = new Intent(DashboardActivity.this, VerifiableActivity.class);
                startActivity(iVerifyGrave);
            }

        });

        //Sets a OnClickListener to wait for the View Past Submissions Button to be clicked and navigate the User
        pastSubsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // On Grave Registry button click will transfer User to Past Subs Activity
                Intent iPastSubs = new Intent(DashboardActivity.this, PastSubsActivity.class);
                startActivity(iPastSubs);
            }

        });

        //Sets a OnClickListener to wait for the Logout Button to be clicked and navigate the User
        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // On Logout button click will Logout User and transfer back to Login page
                Intent iLogin = new Intent(DashboardActivity.this, LoginActivity.class);
                startActivity(iLogin);
                session.logoutUser();
                finish();
            }
        });
    }


}
