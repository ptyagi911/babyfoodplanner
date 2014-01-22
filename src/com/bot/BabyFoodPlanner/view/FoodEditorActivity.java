package com.bot.babyfoodplanner.view;

import java.util.ArrayList;

import com.bot.babyfoodplanner.R;
import com.bot.babyfoodplanner.R.id;
import com.bot.babyfoodplanner.R.layout;
import com.bot.babyfoodplanner.model.FoodManager;
import com.bot.babyfoodplanner.model.FoodManagerHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created with IntelliJ IDEA.
 * User: ptyagi
 * Date: 12/18/13
 * Time: 12:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class FoodEditorActivity extends Activity {
    private String TAG = this.getClass().getSimpleName();
    
	private AlertDialog.Builder dialogBuilder;
    private static FoodManager foodManager;
    
	//FoodGroup fields
    private Spinner spinnerFG;
    private ArrayAdapter<String> spinnerAdapterFG;
    ArrayList<String>foodGroups = new ArrayList<String>();
    private String newFoodGroup = "";
    private EditText inputDialogFG;
    
    //FoodName fields
    private Spinner spinnerFN;
    private ArrayAdapter<String> spinnerAdapterFN;
    ArrayList<String>foodNames = new ArrayList<String>();
    private String newFoodName = "";
    private EditText inputDialogFN;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_editor);

        foodManager = FoodListActivity.getFoodManager();
        foodGroups = foodManager.getFoodGroupsList();
        
        //Food Groups
        spinnerFG = (Spinner) findViewById(R.id.spinner_fg);
        spinnerAdapterFG = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, foodGroups);
        spinnerFG.setAdapter(spinnerAdapterFG);
        
        //Food Names
        spinnerFN = (Spinner) findViewById(R.id.spinner_fn);
        spinnerAdapterFN = new ArrayAdapter<String>(this, 
        		android.R.layout.simple_spinner_dropdown_item, foodNames);
        spinnerFN.setAdapter(spinnerAdapterFN);
    }

    public void addFoodGroup(View view) {
    	dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Enter New Food Group...");
        
    	inputDialogFG = new EditText(this);
        inputDialogFG.setInputType(InputType.TYPE_CLASS_TEXT);
        dialogBuilder.setView(inputDialogFG);
        
        dialogBuilder.setPositiveButton("OK", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				newFoodGroup = inputDialogFG.getText().toString();
				foodGroups.add(newFoodGroup);
				spinnerAdapterFG.notifyDataSetChanged();
			}
		});
        
        dialogBuilder.setNegativeButton("Cancel", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				
			}
		});
        
        dialogBuilder.show();
    
    }
    
    public void removeFoodGroup(View view) {
    	String foodGroup = spinnerFG.getSelectedItem().toString();
    	final int idxFGToBeRemoved = spinnerFG.getSelectedItemPosition();
    	
    	dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Do you want to remove: " + foodGroup + " ?");
        
    	dialogBuilder.setPositiveButton("OK", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				foodGroups.remove(idxFGToBeRemoved);
				spinnerAdapterFG.notifyDataSetChanged();
			}
		});
        
        dialogBuilder.setNegativeButton("Cancel", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				
			}
		});
        
        dialogBuilder.show();
    
    }
    
    //Food Names
    public void addFoodName(View view) {
    	dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Enter New Food Name...");
        
    	inputDialogFN = new EditText(this);
        inputDialogFN.setInputType(InputType.TYPE_CLASS_TEXT);
        dialogBuilder.setView(inputDialogFN);
        
        dialogBuilder.setPositiveButton("OK", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				newFoodName = inputDialogFN.getText().toString();
				foodNames.add(newFoodName);
				spinnerAdapterFN.notifyDataSetChanged();
			}
		});
        
        dialogBuilder.setNegativeButton("Cancel", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				
			}
		});
        
        dialogBuilder.show();
    
    }
    
    public void removeFoodName(View view) {
    	String foodName = spinnerFN.getSelectedItem().toString();
    	final int idxFNToBeRemoved = spinnerFN.getSelectedItemPosition();
    	
    	dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Do you want to remove: " + foodName + " ?");
        
    	dialogBuilder.setPositiveButton("OK", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				foodNames.remove(idxFNToBeRemoved);
				spinnerAdapterFN.notifyDataSetChanged();
			}
		});
        
        dialogBuilder.setNegativeButton("Cancel", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				
			}
		});
        
        dialogBuilder.show();
    
    }
}