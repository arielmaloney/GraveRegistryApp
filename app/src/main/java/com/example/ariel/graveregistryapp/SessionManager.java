package com.example.ariel.graveregistryapp;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * The Session Manager class that will store the Users information
 * Created by Ariel on 3/11/2018.
 */

public class SessionManager {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared Preference file name
    private static final String PREF_NAME = "UserPref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User first name
    public static final String KEY_FIRSTNAME = "first_name";

    // Email address
    public static final String KEY_LASTNAME = "last_name";

    // User id
    public static final String KEY_USERID = "user_id";

    // Email address
    public static final String KEY_EMAIL = "email";


    /**
     * SessionManager constructor
     * @param context the context
     */
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * @param email the user email from login
     * */
    public void createLoginSession(String email){

        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        // URL used to populate all other user info from database
        String HTTP_URL = "http://10.0.2.2/select_user.php?email=" + email;

        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(_context);

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

                            editor.putString(KEY_FIRSTNAME, response.getString("userFirstName"));
                            editor.putString(KEY_LASTNAME, response.getString("userLastName"));
                            editor.putString(KEY_USERID, response.getString("userID"));
                            editor.commit();

                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(_context, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                        Toast.makeText(_context, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );

        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonObjectRequest);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method will check user login status
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect User to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }

    /**
     * Get stored session data
     * @return a HashMap of user data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<>();
        // user first name
        user.put(KEY_FIRSTNAME, pref.getString(KEY_FIRSTNAME, null));

        // user last name
        user.put(KEY_LASTNAME, pref.getString(KEY_LASTNAME, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        //user id
        user.put(KEY_USERID, pref.getString(KEY_USERID, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
        //Toast.makeText(_context, "Please log in or create an account", Toast.LENGTH_SHORT).show();
    }

    /**
     * Check for login status
     * **/
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

}
