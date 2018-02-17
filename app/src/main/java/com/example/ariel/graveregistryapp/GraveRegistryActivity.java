package com.example.ariel.graveregistryapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class GraveRegistryActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grave_registry);

    //This will populate the drop down items for the condition of the grave
    //spinner_condition is the name of the ID in the activity_grave_registry.xml
    Spinner spinner = findViewById(R.id.spinner_condition);

    //This creates a custom adapter (fills our spinner with text) from the array we made in strings.xml, labeled "condition"
    //simple_spinner_item is a custom layout given by android
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.condition, android.R.layout.simple_spinner_item);

    //This sets the layout for the drop down item
    //simple_spinner_dropdown_item is a custom layout given by android
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    //Assigns the custom adapter to our spinner item
    //Now our spinner can view our items in the drop down
    spinner.setAdapter(adapter);

    //This allows our spinner to listen for the selected items from the drop down
    spinner.setOnItemSelectedListener(this);

    spinner.setPrompt("Select Condition");

    }
    /**
     * This method is override from the AdapterView.OnItemSelectedListener
     * It details what the spinner will do when an item is selected
     * parameters: AdapterView - the parent, View - view, i - position, l - time for Toast
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {
        String text = adapterView.getItemAtPosition(i).toString();

        //Toast provides simple feedback about an operation in a small popup
        //Automatically disappears after action is taken or a time is given (l)
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    /**
     * This method is override from the AdapterView.OnItemSelectedListener
     * It details what the spinner will do when an item is not selected - nothing
     * parameters: AdapterView - the parent
     */
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
