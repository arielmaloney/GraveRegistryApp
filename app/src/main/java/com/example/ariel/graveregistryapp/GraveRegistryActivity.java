package com.example.ariel.graveregistryapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class GraveRegistryActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener{

    // URL to the register_grave php file
    private static final String HttpUrl = "http://10.0.2.2/register_grave.php";

    // EditText variables to hold Edit Text values
    EditText et_firstname, et_middlename, et_lastname, et_birth, et_death, et_cemetery, et_conflict, et_rank, et_unit, et_subunit;

    //TextView variables
    TextView locationText;

    // Checkbox
    CheckBox cb_flag_pres, cb_bronze;

    // Submit Button field
    Button submitButton, coordinatesButton;

    // Spinner field
    Spinner spinner;

    // String variables to hold Edit Text values
    String st_userID, st_firstname, st_middlename, st_lastname, st_birth, st_death, st_cemetery, st_conflict, st_rank, st_unit, st_subunit, has_flag, has_holder, st_condition;
    String st_coordinates = "";

    // Volley Request Queue
    RequestQueue requestQueue;

    // Session Manager
    SessionManager session;

    // GPS Tracker
    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grave_registry);

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> userDetails = session.getUserDetails();
        st_userID = userDetails.get(SessionManager.KEY_USERID);

        // Create button
        submitButton = findViewById(R.id.submit_button);
        coordinatesButton = findViewById(R.id.coordinates_button);

        // Getting locationText TextView
        locationText = findViewById(R.id.locationText);


        // Getting EditTexts objects
        et_firstname = findViewById(R.id.et_firstName);
        et_middlename = findViewById(R.id.et_middleName);
        et_lastname = findViewById(R.id.et_lastName);
        et_birth = findViewById(R.id.et_birthYear);
        et_death = findViewById(R.id.et_deathYear);
        et_cemetery = findViewById(R.id.et_cemetery);
        et_conflict = findViewById(R.id.et_conflict);
        et_rank = findViewById(R.id.et_rank);
        et_unit = findViewById(R.id.et_unit);
        et_subunit = findViewById(R.id.et_subUnit);

        // Gets checkbox objects
        cb_bronze = findViewById(R.id.cb_bronzeHolder);
        cb_flag_pres = findViewById(R.id.cb_flag);

        //This will populate the drop down items for the condition of the grave
        //spinner_condition is the name of the ID in the activity_grave_registry.xml
        spinner = findViewById(R.id.spinner_condition);

        //This creates a custom adapter (fills our spinner with text) from the array we made in
        // strings.xml, labeled "condition"
        //simple_spinner_item is a custom layout given by android
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
            this,
            R.array.condition,
            android.R.layout.simple_spinner_item);

        //This sets the layout for the drop down item
        //simple_spinner_dropdown_item is a custom layout given by android
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Assigns the custom adapter to our spinner item
        //Now our spinner can view our items in the drop down
        spinner.setAdapter(adapter);

        //This allows our spinner to listen for the selected items from the drop down
        spinner.setOnItemSelectedListener(this);


        //Sets a OnClickListener to wait for the Get Coordinates Button to be clicked and populate the latitude and longitutde
        coordinatesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                gps = new GPSTracker(getApplicationContext());
                double lat = gps.getLatitude();
                double lon = gps.getLongitude();
                Toast.makeText(getApplicationContext(), "Current location:\n" +
                        "Latitude: " + String.format( "%.2f", lat) + "\n" +
                        "Longitude: " + String.format("%.2f", lon), Toast.LENGTH_LONG).show();
                st_coordinates = "Latitude: " + lat + "  Longitude: " + lon;
                locationText.setText("Current Coordinates:\nLatitude: " + String.format( "%.2f", lat) + "\n" +
                        "Longitude: " + String.format("%.2f", lon));
            }

        });

        //Sets a OnClickListener to wait for the Grave Registry Button to be clicked and navigate the User
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Create String objects from EditTexts
                st_cemetery = et_cemetery.getText().toString();
                st_conflict = et_conflict.getText().toString();
                st_firstname = et_firstname.getText().toString();
                st_lastname = et_lastname.getText().toString();
                st_middlename = et_middlename.getText().toString();
                st_birth = et_birth.getText().toString();
                st_death = et_death.getText().toString();
                st_rank = et_rank.getText().toString();
                st_unit = et_unit.getText().toString();
                st_subunit = et_subunit.getText().toString();
                st_condition = spinner.getSelectedItem().toString();

                // Get checkbox objects
                if (cb_flag_pres.isChecked()) {
                    has_flag = "1";
                }
                else {
                    has_flag = "0";
                }
                if (cb_bronze.isChecked()) {
                    has_holder = "1";
                }
                else {
                    has_holder = "0";
                }


                if (st_lastname.matches("")) {
                    Toast.makeText(GraveRegistryActivity.this, "Last name is required.", Toast.LENGTH_SHORT). show();
                    return;
                }
                else if (st_cemetery.matches("")) {
                    Toast.makeText(GraveRegistryActivity.this, "Cemetery is required.", Toast.LENGTH_SHORT). show();
                    return;
                }
                else if (st_conflict.matches("")) {
                    Toast.makeText(GraveRegistryActivity.this, "Conflict is required.", Toast.LENGTH_SHORT). show();
                    return;
                }
                else if (!st_birth.matches("") && !st_birth.matches("\\d{4}$")) {
                    Toast.makeText(GraveRegistryActivity.this, "Birth year must be 4 digit number.", Toast.LENGTH_SHORT). show();
                    return;
                }
                else if (!st_death.matches("") && !st_death.matches("\\d{4}$")) {
                    Toast.makeText(GraveRegistryActivity.this, "Death year must be 4 digit number.", Toast.LENGTH_SHORT). show();
                    return;
                }
                else if (st_coordinates.matches("")) {
                    Toast.makeText(GraveRegistryActivity.this, "Click the 'Get Coordinates' button to save your location", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (st_condition.matches("Select Condition")) {
                    Toast.makeText(GraveRegistryActivity.this, "Please select a condition.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {

                    AlertDialog alertDialog = new AlertDialog.Builder(GraveRegistryActivity.this).create();
                    alertDialog.setTitle("Register this grave marker?");
                    alertDialog.setMessage("Are you sure you want to register this grave marker?");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    registerGraveMarker();
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
            }

        });

    }




    /**
     * This method is override from the AdapterView.OnItemSelectedListener
     * It details what the spinner will do when an item is selected
     * It also can pop-up what you've selected at the bottom - I've not done that
     * parameters: AdapterView - the parent, View - view, i - position, l - time for Toast
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {
    }

    /**
     * This method is override from the AdapterView.OnItemSelectedListener
     * It details what the spinner will do when an item is not selected - nothing
     * parameters: AdapterView - the parent
     */
    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {
    }


    // Will register the grave marker
    public void registerGraveMarker() {

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        if (ServerResponse.matches("Grave Registration Successful!")) {

                            // Showing response message coming from server.
                            Toast.makeText(GraveRegistryActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(GraveRegistryActivity.this, DashboardActivity.class);
                            startActivity(intent);

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Showing error message if something goes wrong.
                        Toast.makeText(GraveRegistryActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<>();

                // Adding All values to Params.
                params.put("user", st_userID);
                params.put("conflict", st_conflict);
                params.put("rank", st_rank);
                params.put("first_name", st_firstname);
                params.put("last_name", st_lastname);
                params.put("middle_name", st_middlename);
                params.put("birth_date", st_birth);
                params.put("death_date", st_death);
                params.put("unit", st_unit);
                params.put("sub_unit", st_subunit);
                params.put("cemetery", st_cemetery);
                params.put("coordinates", st_coordinates);
                params.put("the_condition", st_condition);
                params.put("flag_present", has_flag);
                params.put("holder_present", has_holder);

                return params;
            }

        };

        // Creating RequestQueue.
        requestQueue = Volley.newRequestQueue(GraveRegistryActivity.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }
}
