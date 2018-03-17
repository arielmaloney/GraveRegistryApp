package com.example.ariel.graveregistryapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PastSubsActivity extends AppCompatActivity {

    SessionManager session;

    String userId, userFirstName, userLastName, HTTP_URL, num;

    int counter;

    TextView entries, displayName, pastSubmissionNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_subs);

        // Creating a new SessionManager object
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> userDetails = session.getUserDetails();

        //Getting the session UserID
        userId = userDetails.get(SessionManager.KEY_USERID);
        userFirstName = userDetails.get(SessionManager.KEY_FIRSTNAME);
        userLastName = userDetails.get(SessionManager.KEY_LASTNAME);

        counter = 0;

        // Setting the text views
        entries = findViewById(R.id.entries);
        displayName = findViewById(R.id.user_displayName);
        pastSubmissionNum = findViewById(R.id.num_past_sub);
        displayName.setText(userFirstName + " " + userLastName);
        pastSubmissionNum.setText("Number of Past Submissions: ");
        entries.setText("");


        // Sending the UserID to the PHP file
        HTTP_URL = "http://10.0.2.2/get_past_submissions.php?user=" + userId;

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

                            JSONArray array = response.getJSONArray("pastSubs");

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
                                String isVerified = entry.getString("is_verified");

                                // Append data if available
                                entries.append("Submission " + (i+1) + ":\n");
                                entries.append("Name: " + firstName + middleName + lastName + "\n");
                                entries.append("Cemetery: " + cemetery + "\n");
                                entries.append("Conflict: " + conflict + "\n");
                                if (!rank.matches("")) {
                                    entries.append("Rank: " + rank + "\n");
                                }
                                if (!unit.matches("")) {
                                    entries.append("Unit: " + unit + "\n");
                                }
                                if (!subUnit.matches("")) {
                                    entries.append("Sub Unit: " + subUnit + "\n");
                                }
                                entries.append("Marker condition: " + condition + "\n");
                                if (flagPresent.matches("0")) {
                                    entries.append("Flag present: No\n");
                                }
                                else {
                                    entries.append("Flag present: Yes\n");
                                }
                                if (holderPresent.matches("0")) {
                                    entries.append("Flag holder present: No\n");
                                }
                                else {
                                    entries.append("Flag holder present: Yes\n");
                                }
                                entries.append(coordArray[0] + " " + coordArray[1] + "\n" + coordArray[3] + " " + coordArray[4] + "\n");
                                if (isVerified.matches("0")) {
                                    entries.append("Verified: No\n");
                                }
                                else {
                                    entries.append("Verified: Yes\n");
                                }
                                entries.append("\n\n");

                                ++counter;
                                pastSubmissionNum.setText("Number of Past Submissions: " + array.length());

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
}
