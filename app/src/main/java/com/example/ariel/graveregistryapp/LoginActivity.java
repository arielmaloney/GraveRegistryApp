package com.example.ariel.graveregistryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Retrieves the values from the EditText fields
        final EditText signin_email = (EditText) findViewById(R.id.et_email_signin);
        final EditText signin_password = (EditText) findViewById(R.id.et_password_signin);
        final Button signInButton = findViewById(R.id.sign_in_button);



        // A listener that waits for the signInButton to be pressed
        signInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                //Creates string objects of the email and password entered
                final String signin_email_string = signin_email.getText().toString();
                final String signin_password_string = signin_password.getText().toString();

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
                // On Sign In button click will transfer User to Dashboard
                Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(i);

            }
        });
    }

    // Sends the User to the Register Activity when the TextView is clicked
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
