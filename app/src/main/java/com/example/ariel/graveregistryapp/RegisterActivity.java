package com.example.ariel.graveregistryapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private static final String HttpUrl = "http://localhost/RegisterUser.php";

    EditText register_email, register_firstname, register_lastname, register_password, register_confirmpass;
    Button registerButton;
    String register_firstName_string, register_lastName_string, register_email_string,
        register_password_string, register_confirmpass_string;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Retrieves the values from the EditText fields
        register_email = findViewById(R.id.et_email_register);
        register_firstname = findViewById(R.id.et_firstname_reg);
        register_lastname = findViewById(R.id.et_lastname_reg);
        register_password = findViewById(R.id.et_reg_password);
        register_confirmpass = findViewById(R.id.et_confrimPassword);
        registerButton = findViewById(R.id.register_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                // Creates string objects from the EditText objects
                register_email_string = register_email.getText().toString();
                register_firstName_string = register_firstname.getText().toString();
                register_lastName_string = register_lastname.getText().toString();
                register_password_string = register_password.getText().toString();
                register_confirmpass_string = register_confirmpass.getText().toString();

                if (register_email_string.matches("") || register_firstName_string.matches("")
                        || register_lastName_string.matches("") || register_password_string.matches("")
                        || register_confirmpass_string.matches("")) {
                    Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (!isEmailValid(register_email_string)) {
                    Toast.makeText(RegisterActivity.this, "Not a valid email address.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (!register_password_string.matches(register_confirmpass_string)) {
                    Toast.makeText(RegisterActivity.this, "The passwords do not match.", Toast.LENGTH_SHORT). show();
                    return;
                }
                else {
                    UserRegistration();
                }




            }
        });
    }

    public void UserRegistration(){

        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
        progressDialog.show();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing Echo Response Message Coming From Server.
                        Toast.makeText(RegisterActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(RegisterActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
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

    public void goToLogin(View view)
    {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    //Determines if email address is valid
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
