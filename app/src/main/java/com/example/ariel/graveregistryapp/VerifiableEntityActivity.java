package com.example.ariel.graveregistryapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Verifiable Entity Activity
 */
public class VerifiableEntityActivity extends AppCompatActivity {

    // The text view for the entry data
    TextView entryData;

    // Buttons for verify as Accurate and suggest a change
    Button verifyAccurate, suggestChange;

    // original entry ID
    String entryID;

    // String variables for data
    String st_conflict, st_rank, st_firstName, st_middleName, st_lastName, st_birth, st_death, st_unit, st_subUnit, st_lat, st_lon, st_cemetery, st_condition, st_flag, st_holder;

    /**
     * The onCreate method for the Verifiable Entity Activity
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifiable_entity);

        // Retrieve the previous intent extra and set original entry ID
        Intent intent = getIntent();
        entryID = intent.getStringExtra("entryID");

        // Set the entryData TextView
        entryData = findViewById(R.id.verifiable_entity_data);
        entryData.setText("");

        // Populate the TextView with the data from the database
        getEntryData(entryID);

        // Setting buttons from view
        verifyAccurate = findViewById(R.id.verify_accurate_button);
        suggestChange = findViewById(R.id.suggest_change_button);

        // Set an OnClickListener to wait for the verify as accurate button to be pushed
        verifyAccurate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Display dialog box for user to decide if they are sure they want to verify as accurate
                AlertDialog alertDialog = new AlertDialog.Builder(VerifiableEntityActivity.this).create();
                alertDialog.setTitle("Verify as Accurate?");
                alertDialog.setMessage("Are you sure you want to verify this submission as accurate?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // verifies the marker as accurate in the database
                                verifyMarker(entryID);
                                // take User back to the Dashboard
                                Intent dash = new Intent(VerifiableEntityActivity.this, DashboardActivity.class);
                                startActivity(dash);
                                dialog.dismiss();

                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();



            }
        });

        // Set an OnClickListener to wait for the suggest a change button to be pushed
        suggestChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pass the entry information to the next activity
                Intent change = new Intent(VerifiableEntityActivity.this, ChangeSubActivity.class);
                change.putExtra("entryId", entryID);
                change.putExtra("conflict", st_conflict);
                change.putExtra("rank", st_rank);
                change.putExtra("first_name", st_firstName);
                change.putExtra("last_name", st_lastName);
                change.putExtra("middle_name", st_middleName);
                change.putExtra("birth_date", st_birth);
                change.putExtra("death_date", st_death);
                change.putExtra("unit", st_unit);
                change.putExtra("subUnit", st_subUnit);
                change.putExtra("cemetery", st_cemetery);
                change.putExtra("lat", st_lat);
                change.putExtra("lon", st_lon);
                change.putExtra("condition", st_condition);
                change.putExtra("flag", st_flag);
                change.putExtra("holder", st_holder);
                startActivity(change);
            }
        });


    }

    /**
     * A method to populate grave marker data for the User to review
     * @param id grave marker id
     */
    void getEntryData(String id) {

        String HTTP_URL = "http://10.0.2.2/get_grave_entry.php?Marker_ID=" + id;

        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                HTTP_URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Process the JSON
                        try{

                            JSONArray array = response.getJSONArray("graveEntry");

                            //Loop through the array elements
                            for(int i = array.length() - 1; i >= 0; i--) {
                                // Get current JSON object
                                JSONObject entry = array.getJSONObject(i);

                                //Get current data
                                String conflict = entry.getString("conflict");
                                String rank = entry.getString("rank");
                                String firstName = entry.getString("first_name");
                                String lastName = entry.getString("last_name");
                                String middleName = entry.getString("middle_name");
                                String birthDate = entry.getString("birth_date");
                                String deathDate = entry.getString("death_date");
                                String unit = entry.getString("unit");
                                String subUnit = entry.getString("sub_unit");
                                String cemetery = entry.getString("cemetery");
                                String coordinates = entry.getString("coordinates");
                                String[] coordArray = coordinates.split(" ");
                                String condition = entry.getString("the_condition");
                                String flagPresent = entry.getString("flag_present");
                                String holderPresent = entry.getString("holder_present");

                                // Save data in String to be passed to Change Sub Activity if needed
                                st_conflict = conflict;
                                st_rank = rank;
                                st_firstName = firstName;
                                st_lastName = lastName;
                                st_middleName = middleName;
                                st_birth = birthDate;
                                st_death = deathDate;
                                st_unit = unit;
                                st_subUnit = subUnit;
                                st_cemetery = cemetery;
                                st_lat = coordArray[1];
                                st_lon = coordArray[4];
                                st_condition = condition;
                                st_flag = flagPresent;
                                st_holder = holderPresent;


                                // Append data if available
                                entryData.append("Name: " + firstName + " " + middleName + " " + lastName + "\n");
                                entryData.append("Cemetery: " + cemetery + "\n");
                                entryData.append("Conflict: " + conflict + "\n");
                                if (!birthDate.matches("")) {
                                    entryData.append("Birth year: " + birthDate + "\n");
                                }
                                if (!deathDate.matches("")) {
                                    entryData.append("Death year: " + deathDate + "\n");
                                }
                                if (!rank.matches("")) {
                                    entryData.append("Rank: " + rank + "\n");
                                }
                                if (!unit.matches("")) {
                                    entryData.append("Unit: " + unit + "\n");
                                }
                                if (!subUnit.matches("")) {
                                    entryData.append("Sub Unit: " + subUnit + "\n");
                                }
                                entryData.append("Marker condition: " + condition + "\n");
                                if (flagPresent.matches("0")) {
                                    entryData.append("Flag present: No\n");
                                }
                                else {
                                    entryData.append("Flag present: Yes\n");
                                }
                                if (holderPresent.matches("0")) {
                                    entryData.append("Flag holder present: No\n");
                                }
                                else {
                                    entryData.append("Flag holder present: Yes\n");
                                }
                                entryData.append(coordArray[0] + " " + coordArray[1] + "\n" + coordArray[3] + " " + coordArray[4] + "\n");

                                entryData.append("\n\n");

                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Display Toast when error occurs
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );

        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonObjectRequest);

    }


    /**
     * Verifies the marker in the database
     * @param id the grave marker id
     */
    public void verifyMarker(String id) {

        String HttpUrl = "http://10.0.2.2/verify_accurate.php?Marker_ID=" + id;
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Show response message coming from server
                        Toast.makeText(VerifiableEntityActivity.this, ServerResponse, Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Show error message if something goes wrong
                        Toast.makeText(VerifiableEntityActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params
                Map<String, String> params = new HashMap<>();

                // Adding values to Params
                params.put("Marker_ID", entryID);
                params.put("is_verified", "1");
                return params;
            }

        };

        // Creating RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(VerifiableEntityActivity.this);

        // Adding the StringRequest object into requestQueue
        requestQueue.add(stringRequest);
    }

}
