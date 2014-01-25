package com.bot.babyfoodplanner.view;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.bot.babyfoodplanner.R;
import com.bot.babyfoodplanner.model.FoodManager;

/**
 * Created with IntelliJ IDEA.
 * User: ptyagi
 * Date: 12/18/13
 * Time: 12:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class FoodEditorActivity extends Activity implements OnItemSelectedListener {
    private String TAG = this.getClass().getSimpleName();
    
	private AlertDialog.Builder fgDialogBuilder;
	private AlertDialog.Builder fnDialogBuilder;
	
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
    
    private FoodManager mFoodManager;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_editor);
        
        mFoodManager = FoodManager.getInstance(getApplicationContext());
        
        foodManager = FoodListActivity.getFoodManager();
        foodGroups = foodManager.getFoodGroupsList();
        
        //Food Groups
        spinnerFG = (Spinner) findViewById(R.id.spinner_fg);
        spinnerAdapterFG = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, foodGroups);
        spinnerFG.setAdapter(spinnerAdapterFG);
        spinnerFG.setOnItemSelectedListener(this);
        
//        String fgKey = spinnerFG.getSelectedItem().toString();
//        foodNames = mFoodManager.getFoodNamesList(fgKey);
//        //Food Names
//        spinnerFN = (Spinner) findViewById(R.id.spinner_fn);
//        spinnerAdapterFN = new ArrayAdapter<String>(this, 
//        		android.R.layout.simple_spinner_dropdown_item, foodNames);
//        spinnerFN.setAdapter(spinnerAdapterFN);
    }

    public void addFoodGroup(View view) {
    	fgDialogBuilder = new AlertDialog.Builder(this);
        fgDialogBuilder.setTitle("Enter New Food Group...");
        
    	inputDialogFG = new EditText(this);
        inputDialogFG.setInputType(InputType.TYPE_CLASS_TEXT);
        fgDialogBuilder.setView(inputDialogFG);
        
        fgDialogBuilder.setPositiveButton("OK", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				newFoodGroup = inputDialogFG.getText().toString();
				foodGroups.add(newFoodGroup);
				foodManager.addNewFoodGroup(newFoodGroup);
				spinnerAdapterFG.notifyDataSetChanged();
			}
		});
        
        fgDialogBuilder.setNegativeButton("Cancel", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				
			}
		});
        
        fgDialogBuilder.show();
    
    }
    
    public void removeFoodGroup(View view) {
    	String foodGroup = spinnerFG.getSelectedItem().toString();
    	final int idxFGToBeRemoved = spinnerFG.getSelectedItemPosition();
    	
    	fgDialogBuilder = new AlertDialog.Builder(this);
        fgDialogBuilder.setTitle("Do you want to remove: " + foodGroup + " ?");
        
    	fgDialogBuilder.setPositiveButton("OK", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				foodGroups.remove(idxFGToBeRemoved);
				spinnerAdapterFG.notifyDataSetChanged();
			}
		});
        
        fgDialogBuilder.setNegativeButton("Cancel", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				
			}
		});
        
        fgDialogBuilder.show();
    
    }
    
    //Food Names
    public void addFoodName(View view) {
    	fnDialogBuilder = new AlertDialog.Builder(this);
        fnDialogBuilder.setTitle("Enter New Food Name...");
        
    	inputDialogFN = new EditText(this);
        inputDialogFN.setInputType(InputType.TYPE_CLASS_TEXT);
        fnDialogBuilder.setView(inputDialogFN);
        
        fnDialogBuilder.setPositiveButton("OK", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				newFoodName = inputDialogFN.getText().toString();
				foodNames.add(newFoodName);
				updateFoodBank();
				spinnerAdapterFN.notifyDataSetChanged();
			}
		});
        
        fnDialogBuilder.setNegativeButton("Cancel", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				
			}
		});
        
        fnDialogBuilder.show();
    
    }
    
    public void removeFoodName(View view) {
    	String foodName = spinnerFN.getSelectedItem().toString();
    	final int idxFNToBeRemoved = spinnerFN.getSelectedItemPosition();
    	
    	fnDialogBuilder = new AlertDialog.Builder(this);
        fnDialogBuilder.setTitle("Do you want to remove: " + foodName + " ?");
        
    	fnDialogBuilder.setPositiveButton("OK", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				foodNames.remove(idxFNToBeRemoved);
				updateFoodBank();
				spinnerAdapterFN.notifyDataSetChanged();
			}
		});
        
        fnDialogBuilder.setNegativeButton("Cancel", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				
			}
		});
        
        fnDialogBuilder.show();
    
    }

    public void updateFoodBank() {
    	String fgKey = spinnerFG.getSelectedItem().toString();
    	foodManager.updateFoodBank(fgKey, foodNames);
    }
    
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		String fgKey = spinnerFG.getSelectedItem().toString();
        foodNames = mFoodManager.getFoodNamesList(fgKey);
        //Food Names
        spinnerFN = (Spinner) findViewById(R.id.spinner_fn);
        spinnerAdapterFN = new ArrayAdapter<String>(this, 
        		android.R.layout.simple_spinner_dropdown_item, foodNames);
        spinnerFN.setAdapter(spinnerAdapterFN);
        Log.d("Priyanka", foodNames.toString());
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}