package com.bot.babyfoodplanner;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created with IntelliJ IDEA.
 * User: ptyagi
 * Date: 12/18/13
 * Time: 12:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class FoodEditor extends Activity {
    private Spinner spinnerFG;
    private ArrayAdapter<String> spinnerAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_editor);


        spinnerFG = (Spinner) findViewById(R.id.spinner_fg);
        spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, getFoodGroups());
        spinnerFG.setAdapter(spinnerAdapter);
    }

    private String[] getFoodGroups() {
        String[] foodGroups = {"Fruits", "Veggies"};
        return foodGroups;
    }
}