package com.bot.babyfoodplanner.model;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: PTyagi
 * Date: 11/15/13
 * Time: 2:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class FoodManager {
    final String TAG = this.getClass().getSimpleName();

    private static FoodManagerHelper mHelper = null;
    private static Context mContext = null;

    private static String[] foodGroups;
    private static String[][] foodNames;
    private static HashMap<Integer, ArrayList<String>> foodBankMap = new HashMap<Integer, ArrayList<String>>();

    private static Integer foodBankSize = 0;
    
    public FoodManager(Context context) {
        mContext = context;
        mHelper = FoodManagerHelper.getInstance(context);
    }

    public void parseFoodGroups() throws JSONException {
        JSONArray foodBank = mHelper.retrieveFoodBank();
        foodGroups = new String[foodBank.length()];
        foodBankSize = foodBank.length();
        
        for (int i=0; i < foodBankSize; i++) {
            JSONObject fg = foodBank.getJSONObject(i);

            if (fg.has(FoodLiterals.FG_LENTILS)) {
                String fgKey = FoodLiterals.FG_LENTILS;
                //foodGroups = key;
                assign(fg, fgKey, i);
            } else if (fg.has(FoodLiterals.FG_VEGGIES)) {
                String fgKey = FoodLiterals.FG_VEGGIES;
                assign(fg, fgKey, i);
            } else if (fg.has(FoodLiterals.FG_FRUITS)) {
                String fgKey = FoodLiterals.FG_FRUITS;
                assign(fg, fgKey, i);
            } else if (fg.has(FoodLiterals.FG_DAIRY)) {
                String fgKey = FoodLiterals.FG_DAIRY;
                assign(fg, fgKey, i);
            } else if (fg.has(FoodLiterals.FG_GRAINS)) {
                String fgKey = FoodLiterals.FG_GRAINS;
                assign(fg, fgKey, i);
            }
        }
    }

    //TODO
    public void addFoodItem(int foodGroup, String foodName) {
        if (foodBankMap.containsKey(foodGroup)) {
            ArrayList<String> foodNames = foodBankMap.get(foodGroup);
            foodNames.add(foodName);
            foodBankMap.put(foodGroup, foodNames);
        } else {
            //Food Group doesn't exist
            Log.e(TAG, "Food group doesn't exist");
        }
    }

    private void assign(JSONObject foodGroup, 
    		String foodGroupKey, int groupIndex) throws JSONException {
        JSONObject foodItems = foodGroup.getJSONObject(foodGroupKey);
        Iterator<String> keys = foodItems.keys();

        ArrayList<String> foodNamesList = new ArrayList<String>();
        foodNames = new String[foodBankSize][foodItems.length()];
        String foods[] = new String[foodItems.length()];
        int counter = 0;
        while(keys.hasNext() ){
            String key = (String)keys.next();
            if(foodItems.get(key) instanceof String ){
                foods[counter++] = foodItems.getString(key);
                foodNamesList.add(foodItems.getString(key));
            }
        }

        foodGroups[groupIndex] = foodGroupKey;

        foodBankMap.put(groupIndex, foodNamesList);
    }

    public String[] getFoodGroups() {
        return foodGroups;
    }

    public ArrayList<String> getFoodGroupsList() {
    	return new ArrayList<String>(Arrays.asList(foodGroups));
    }
    
    public String[][] getFoodNames() {

        Iterator<?> iterator = foodBankMap.keySet().iterator();
        int counter = 0;

        for (Map.Entry pair : foodBankMap.entrySet()) {
            Integer key = (Integer)pair.getKey();
            ArrayList<String> foodList = (ArrayList<String>)pair.getValue();

            for(int i=0; i < foodList.size(); i++) {
                foodNames[key][i] = (String)foodList.get(i);
            }
        }
        return foodNames;
    }
    
    public void persistFoodBank(
    		HashMap<Integer, ArrayList<String>> foodBankMap) {
    	
    }
}
