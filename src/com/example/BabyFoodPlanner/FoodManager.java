package com.example.BabyFoodPlanner;

import org.json.JSONArray;
import android.view.*;
import android.app.*;
import android.content.Context;
import org.json.JSONObject;
import org.json.*;
import java.util.Iterator;

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

    public FoodManager(Context context) {
        mContext = context;
        mHelper = FoodManagerHelper.getInstance(context);
    }

    public void parseFoodGroups() throws JSONException {
        JSONArray foodBank = mHelper.retrieveFoodBank();

        for (int i=0; i < foodBank.length(); i++) {
            JSONObject fg = foodBank.getJSONObject(i);

            if (fg.has(FoodLiterals.FG_LENTILS)) {
                String fgKey = FoodLiterals.FG_LENTILS;
                //foodGroups = key;
                assign(fg, fgKey, i);
            }
        }
    }

    private void assign(JSONObject foodGroup, String foodGroupKey, int groupIndex) throws JSONException {
        JSONObject foodItems = foodGroup.getJSONObject(foodGroupKey);
        Iterator<String> keys = foodItems.keys();

        String foods[] = new String[2];
        int counter = 0;
        while(keys.hasNext() ){
            String key = (String)keys.next();
            if(foodItems.get(key) instanceof JSONObject ){
                foods[counter++] = foodItems.getString(key);
            }
        }

        foodGroups[groupIndex] = foodGroupKey;
        foodNames[groupIndex] = foods;
    }

    public String[] getFoodGroups() {
        return foodGroups;
    }

    public String[][] getFoodNames() {
        return foodNames;
    }
}
