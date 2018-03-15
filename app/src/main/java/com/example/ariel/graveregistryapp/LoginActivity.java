package com.example.ariel.graveregistryapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    // Edit Text variables
    EditText signin_email, signin_password;

    // Button variable
    Button signInButton;

    // Creating Volley RequestQueue
    RequestQueue requestQueue;

    // String variables to hold EditText values
    String signin_email_string, signin_password_string;

    // Create a progress dialog
    ProgressDialog progressDialog;

    // The url for the login_user php file
    String HttpUrl = "http://10.0.2.2/login_user.php";

    // Session Manager
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(getApplicationContext());

        // Retrieves the values from the EditText fields
        signin_email = findViewById(R.id.et_email_signin);
        signin_password = findViewById(R.id.et_password_signin);
        signInButton = findViewById(R.id.sign_in_button);

        //Creating the request queue
        requestQueue = Volley.newRequestQueue(LoginActivity.this);

        // Assign Activity this to progress dialog
        progressDialog = new ProgressDialog(LoginActivity.this);

        // A listener that waits for the signInButton to be pressed
        signInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                //Creates string objects of the email and password entered
                signin_email_string = signin_email.getText().toString();
                signin_password_string = signin_password.getText().toString();

                // If fields are left empty than send error message to User
                if (signin_email_string.matches("")) {
                    Toast.makeText(LoginActivity.this, "Email is required.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (signin_password_string.matches("")) {
                    Toast.makeText(LoginActivity.this, "Password is required.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (!isEmailValid(signin_email_string)) {
                    Toast.makeText(LoginActivity.this, "Not a valid email address.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    // Showing progress dialog at user registration time.
                    progressDialog.setMessage("Please Wait");
                    progressDialog.show();

                    // Creating string request with post method.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String ServerResponse) {

                                    // Hiding the progress dialog after all task complete.
                                    progressDialog.dismiss();

                                    // Matching server responce message to our text.
                                    if(ServerResponse.matches("Successful Login!")) {

                                        // If response matched then show the toast.
                                        Toast.makeText(LoginActivity.this, ServerResponse, Toast.LENGTH_LONG).show();

                                        session.createLoginSession(signin_email_string);

                                        // Opening the user profile activity using intent.
                                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                        //intent.putExtra("email", signin_email_string);
                                        startActivity(intent);
                                        // Finish the current Login activity.
                                        finish();
                                    }
                                    else {

                                        // Showing Echo Response Message Coming From Server.
                                        Toast.makeText(LoginActivity.this, ServerResponse, Toast.LENGTH_LONG).show();

                                    }


                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {

                                    // Hiding the progress dialog after all task complete.
                                    progressDialog.dismiss();

                                    // Showing error message if something goes wrong.
                                    Toast.makeText(LoginActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() {

                            // Creating Map String Params.
                            Map<String, String> params = new HashMap<>();

                            // Adding All values to Params.
                            // The first argument should be same as the database table columns.
                            params.put("email", signin_email_string);
                            params.put("password", signin_password_string);

                            return params;
                        }

                    };

                    // Creating RequestQueue.
                    RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);

                    // Adding the StringRequest object into requestQueue.
                    requestQueue.add(stringRequest);
                }

            }
        });
    }

    // Sends the User to the Register Activity when the TextView is clicked
    // android:onClick="goToRegister" is placed in the xml for the TextView
    public void goToRegister(View view)
    {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    //Determines if email address is valid
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


}
