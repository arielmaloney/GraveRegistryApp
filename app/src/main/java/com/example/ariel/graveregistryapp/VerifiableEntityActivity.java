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

public class VerifiableEntityActivity extends AppCompatActivity {

    TextView entryData;

    Button verifyAccurate, suggestChange;

    String entryID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifiable_entity);

        Intent intent = getIntent();
        entryID = intent.getStringExtra("entryID");

        entryData = findViewById(R.id.verifiable_entity_data);

        entryData.setText("");

        getEntryData(entryID);

        // Getting buttons from view
        verifyAccurate = findViewById(R.id.verify_accurate_button);
        suggestChange = findViewById(R.id.suggest_change_button);

        verifyAccurate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog alertDialog = new AlertDialog.Builder(VerifiableEntityActivity.this).create();
                alertDialog.setTitle("Verify as Accurate?");
                alertDialog.setMessage("Are you sure you want to verify this submission as accurate?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                verifyMarker(entryID);
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

        suggestChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent change = new Intent(VerifiableEntityActivity.this, ChangeSubActivity.class);
                change.putExtra("entryId", entryID);
                startActivity(change);
            }
        });


    }

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
                                String firstName = entry.getString("first_name") + " ";
                                String lastName = entry.getString("last_name");
                                String middleName = entry.getString("middle_name") + " ";
                                String unit = entry.getString("unit");
                                String subUnit = entry.getString("sub_unit");
                                String cemetery = entry.getString("cemetery");
                                String coordinates = entry.getString("coordinates");
                                String[] coordArray = coordinates.split(" ");
                                String condition = entry.getString("the_condition");
                                String flagPresent = entry.getString("flag_present");
                                String holderPresent = entry.getString("holder_present");

                                // Append data if available
                                entryData.append("Name: " + firstName + middleName + lastName + "\n");
                                entryData.append("Cemetery: " + cemetery + "\n");
                                entryData.append("Conflict: " + conflict + "\n");
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
                        // Do something when error occurred
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );

        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonObjectRequest);

    }


    public void verifyMarker(String id) {

        String HttpUrl = "http://10.0.2.2/verify_accurate.php?Marker_ID=" + id;
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Showing response message coming from server.
                        Toast.makeText(VerifiableEntityActivity.this, ServerResponse, Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Showing error message if something goes wrong.
                        Toast.makeText(VerifiableEntityActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<>();

                // Adding All values to Params.
                params.put("Marker_ID", entryID);
                params.put("is_verified", "1");
                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(VerifiableEntityActivity.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

}
