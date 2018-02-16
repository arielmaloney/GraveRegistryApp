package com.example.ariel.graveregistryapp;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Retrieves the values from the EditText fields
        final EditText register_email = (EditText) findViewById(R.id.et_email_register);



        final Button signInButton = findViewById(R.id.register_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                // Creates string objects from the EditText objects
                final String register_email_string = register_email.getText().toString();

                if (!isEmailValid(register_email_string)) {
                    Toast.makeText(RegisterActivity.this, "Not a valid email address.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // On Sign In button click will transfer User to Dashboard
                Intent i = new Intent(RegisterActivity.this, DashboardActivity.class);
                startActivity(i);

            }
        });
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
