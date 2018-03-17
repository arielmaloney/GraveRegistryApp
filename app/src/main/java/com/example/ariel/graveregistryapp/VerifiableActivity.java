package com.example.ariel.graveregistryapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

public class VerifiableActivity extends AppCompatActivity {

    public ListView list;
    public ArrayList<VerifiableEntry> entries = new ArrayList<>();
    public VerifiableListAdapter adapter;

    public SessionManager session;

    public String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifiable);

        // Create a session and get userID
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        userID = user.get(SessionManager.KEY_USERID);

        getData(userID);


        // Creating the list and list adapter
        list = findViewById(R.id.listViewVerify);
        adapter = new VerifiableListAdapter(this);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String entryID = entries.get(i).getId();
                Intent iVerify = new Intent(VerifiableActivity.this, VerifiableEntityActivity.class);
                iVerify.putExtra("entryID", entryID);
                startActivity(iVerify);
            }
        });


    }

    public void getData(String user) {

        String HTTP_URL = "http://10.0.2.2/get_verifiable.php?user=" + user;

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

                            JSONArray array = response.getJSONArray("verifiable");

                            //Loop through the array elements
                            for(int i = array.length() - 1; i >= 0; i--) {
                                // Get current JSON object
                                JSONObject entry = array.getJSONObject(i);

                                VerifiableEntry ve = new VerifiableEntry();

                                //Get current data
                                String id = entry.getString("Marker_ID");
                                String conflict = entry.getString("conflict");
                                String firstName = entry.getString("first_name") + " ";
                                String lastName = entry.getString("last_name");
                                String middleName = entry.getString("middle_name") + " ";
                                String cemetery = entry.getString("cemetery");
                                String coordinates = entry.getString("coordinates");
                                String[] coordArray = coordinates.split(" ");

                                ve.setId(id);
                                ve.setName(firstName + middleName + lastName);
                                ve.setCemetery(cemetery);
                                ve.setConflict(conflict);

                                entries.add(ve);



                            }

                            adapter.notifyDataSetChanged();

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
