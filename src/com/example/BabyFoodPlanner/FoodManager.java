package com.example.BabyFoodPlanner;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: PTyagi
 * Date: 11/15/13
 * Time: 2:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class FoodManager {

    private static FoodManagerHelper mHelper = null;
    private static Context mContext = null;

    private static String[] foodGroups;
    private static String[][] foodNames;
    private static HashMap<Integer, ArrayList<String>> foodNamesMap = new HashMap<Integer, ArrayList<String>>();

    public FoodManager(Context context) {
        mContext = context;
        mHelper = FoodManagerHelper.getInstance(context);
    }

    public void parseFoodGroups() throws JSONException {
        JSONArray foodBank = mHelper.retrieveFoodBank();
        foodGroups = new String[foodBank.length()];

        for (int i=0; i < foodBank.length(); i++) {
            JSONObject fg = foodBank.getJSONObject(i);

            if (fg.has(FoodLiterals.FG_LENTILS)) {
                String fgKey = FoodLiterals.FG_LENTILS;
                //foodGroups = key;
                assign(foodBank.length(), fg, fgKey, i);
            } else if (fg.has(FoodLiterals.FG_VEGGIES)) {
                String fgKey = FoodLiterals.FG_VEGGIES;
                assign(foodBank.length(), fg, fgKey, i);
            } else if (fg.has(FoodLiterals.FG_FRUITS)) {
                String fgKey = FoodLiterals.FG_FRUITS;
                assign(foodBank.length(), fg, fgKey, i);
            } else if (fg.has(FoodLiterals.FG_DAIRY)) {
                String fgKey = FoodLiterals.FG_DAIRY;
                assign(foodBank.length(), fg, fgKey, i);
            } else if (fg.has(FoodLiterals.FG_GRAINS)) {
                String fgKey = FoodLiterals.FG_GRAINS;
                assign(foodBank.length(), fg, fgKey, i);
            }
        }


    }

    private void assign(int foodBankSize, JSONObject foodGroup, String foodGroupKey, int groupIndex) throws JSONException {
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

        //foodNames[groupIndex] = foods;

        foodNamesMap.put(groupIndex, foodNamesList);
    }

    public String[] getFoodGroups() {
        return foodGroups;
    }

    public String[][] getFoodNames() {

        Iterator<?> iterator = foodNamesMap.keySet().iterator();
        int counter = 0;

        for (Map.Entry pair : foodNamesMap.entrySet()) {
            Integer key = (Integer)pair.getKey();
            ArrayList<String> foodList = (ArrayList<String>)pair.getValue();
//            String[] foodArray = new String[foodList.size()];
//            foodArray = foodList.toArray(foodArray);

            for(int i=0; i < foodList.size(); i++) {
                foodNames[key][i] = (String)foodList.get(i);
            }
        }
//        while(iterator.hasNext()) {
//            Map.Entry<Integer, ArrayList<String>> pair = (Map.Entry<Integer, ArrayList<String>>)iterator.next();
//            Integer key = (Integer)pair.getKey();
//            ArrayList<String> foodList = (ArrayList<String>)pair.getValue();
////            String[] foodArray = new String[foodList.size()];
////            foodArray = foodList.toArray(foodArray);
//
//            for(int i=0; i < foodList.size(); i++) {
//                foodNames[key][i] = (String)foodList.get(i);
//            }

            //foodNames[key][counter++] = foodArray;
//        }

        return foodNames;
    }
}
