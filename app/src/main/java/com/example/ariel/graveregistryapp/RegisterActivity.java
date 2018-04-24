package com.example.ariel.graveregistryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * The Register Activity
 */
public class RegisterActivity extends AppCompatActivity {

    // URL to the insert_user php file
    private static final String HttpUrl = "http://10.0.2.2/register_user.php";

    // EditText variables to hold Edit Text values
    EditText register_email, register_firstname, register_lastname, register_password, register_confirmpass;

    // Register button field
    Button registerButton;

    // String variables to hold Edit Text values
    String register_firstName_string, register_lastName_string, register_email_string,
        register_password_string, register_confirmpass_string;

    //Creating Volley RequestQueue
    RequestQueue requestQueue;

    /**
     * The onCreate method for the Register Activity
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Sets the EditText fields
        register_email = findViewById(R.id.et_email_register);
        register_firstname = findViewById(R.id.et_firstname_reg);
        register_lastname = findViewById(R.id.et_lastname_reg);
        register_password = findViewById(R.id.et_reg_password);
        register_confirmpass = findViewById(R.id.et_confrimPassword);
        registerButton = findViewById(R.id.register_button);

        // Creating Volley newRequestQueue
        requestQueue = Volley.newRequestQueue(RegisterActivity.this);

        // Creates an OnClickListener to wait for the register button to be clicked and register the User
        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Creates string objects from the EditText objects
                register_email_string = register_email.getText().toString();
                register_firstName_string = register_firstname.getText().toString();
                register_lastName_string = register_lastname.getText().toString();
                register_password_string = register_password.getText().toString();
                register_confirmpass_string = register_confirmpass.getText().toString();

                // Error handling for required fields and proper formatting
                if (register_email_string.matches("") || register_firstName_string.matches("")
                        || register_lastName_string.matches("") || register_password_string.matches("")
                        || register_confirmpass_string.matches("")) {
                    Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
                else if (!isEmailValid(register_email_string)) {
                    Toast.makeText(RegisterActivity.this, "Not a valid email address.", Toast.LENGTH_SHORT).show();
                }
                else if (!register_password_string.matches(register_confirmpass_string)) {
                    Toast.makeText(RegisterActivity.this, "The passwords do not match.", Toast.LENGTH_SHORT). show();
                }
                else {

                    // Creating string request with post method.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String ServerResponse) {

                                    // Showing response message coming from server.
                                    Toast.makeText(RegisterActivity.this, ServerResponse, Toast.LENGTH_LONG).show();

                                    if (ServerResponse.matches("Successful Registration!")) {
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {

                                    // Showing error message if something goes wrong.
                                    Toast.makeText(RegisterActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() {

                            // Creating Map String Params.
                            Map<String, String> params = new HashMap<>();

                            // Adding All values to Params.
                            params.put("first_name", register_firstName_string);
                            params.put("last_name", register_lastName_string);
                            params.put("email", register_email_string);
                            params.put("password", register_password_string);

                            return params;
                        }

                    };

                    // Creating RequestQueue.
                    RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);

                    // Adding the StringRequest object into requestQueue.
                    requestQueue.add(stringRequest);
                }
            }
        });
    }

    /**
     * Sends the User to the Login screen upon clicking the text view
     * This is set in the login_activity.xml
     * @param view the view
     */
    public void goToLogin(View view)
    {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * Determines if the email is a valid email address
     * @param email the entered email address
     * @return boolean
     */
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
