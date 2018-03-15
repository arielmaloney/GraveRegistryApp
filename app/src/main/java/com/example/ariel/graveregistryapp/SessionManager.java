package com.example.ariel.graveregistryapp;

import java.util.HashMap;
import java.util.Map;

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

    // Sharedpref file name
    private static final String PREF_NAME = "UserPref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User first name (make variable public to access from outside)
    public static final String KEY_FIRSTNAME = "first_name";

    // Email address (make variable public to access from outside)
    public static final String KEY_LASTNAME = "last_name";

    // User id
    public static final String KEY_USERID = "user_id";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    public String firstname = "";
    public String lastname = "";
    public String useremail = "";
    public String userid = "";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String email){

        useremail = email;
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        //populates all other user info
        String HTTP_URL = "http://10.0.2.2/select_user.php?email=" + email;

        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(_context);

        //Hashmap of parameters
        //Map<String, String> params = new HashMap();
        //params.put("email", useremail);

        //JSONObject parameters = new JSONObject(params);

        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                HTTP_URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Do something with response
                        Toast.makeText(_context, "success: " + response.toString(), Toast.LENGTH_SHORT).show();

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
     * Gets User information from database and stores it in the session
     */

    public void getUserInfo() {

        String HTTP_URL = "http://10.0.2.2/select_user.php?email=" + useremail;

        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(_context);

        //Hashmap of parameters
        //Map<String, String> params = new HashMap();
        //params.put("email", useremail);

        //JSONObject parameters = new JSONObject(params);

        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                HTTP_URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Do something with response
                        Toast.makeText(_context, "success: " + response.toString(), Toast.LENGTH_SHORT).show();

                        // Process the JSON
                        try{

                            firstname = response.getString("userFirstName");
                            lastname = response.getString("userLastName");
                            userid = response.getString("userID");
                            Toast.makeText(_context, firstname + lastname + userid, Toast.LENGTH_LONG).show();


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


    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
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
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user first name
        user.put(KEY_FIRSTNAME, pref.getString(KEY_FIRSTNAME, null));
        Toast.makeText(_context, pref.getString(KEY_FIRSTNAME, null), Toast.LENGTH_LONG).show();

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
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

}
