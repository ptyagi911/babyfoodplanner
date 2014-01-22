package com.bot.babyfoodplanner.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created with IntelliJ IDEA.
 * User: PTyagi
 * Date: 11/15/13
 * Time: 12:47 PM
 *
 * Loads default food items
 * Allows User to add more foods groups and food items
 * Allows User to Plan food for the week
 */
public class FoodManagerHelper {

    private static FoodManagerHelper mHelper = null;

    private static SharedPreferences foodBankPrefs = null;

    private static Context mContext;

    public static FoodManagerHelper getInstance(Context context) {
        if (mHelper == null) {
            mHelper = new FoodManagerHelper(context);
        }

        return mHelper;
    }

    private FoodManagerHelper(Context context) {
        if (foodBankPrefs == null) {
            mContext = context;
            foodBankPrefs =
                mContext.getSharedPreferences(FoodLiterals.SHARED_PREFS_FOOD_BANK, Context.MODE_PRIVATE);
        }

        try {
            loadFoodBank();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void loadFoodBank() throws JSONException {
        boolean foodBankKeyExists = (foodBankPrefs != null) ?
                foodBankPrefs.contains(FoodLiterals.SHARED_PREFS_KEY_FOOD_BANK) : false;

        if (!foodBankKeyExists) {
            updateFoodBank(getDefaultFoodBank());
        }
    }
    /**
     * Write JSON Object into SharedPreference
     */
    public void updateFoodBank(JSONArray jsonArray) throws JSONException {
        Editor editor = foodBankPrefs.edit();
        editor.putString(FoodLiterals.SHARED_PREFS_KEY_FOOD_BANK, jsonArray.toString());
        editor.commit();
    }

    /**
     * Reads data from SharedPreference
     * @return
     */
    public JSONArray retrieveFoodBank() throws JSONException {
        String foodItemsStr = foodBankPrefs.getString(
                FoodLiterals.SHARED_PREFS_KEY_FOOD_BANK, getDefaultFoodBank().toString());

        return new JSONArray(foodItemsStr);
    }


    private JSONArray getDefaultFoodBank() throws JSONException {
        JSONArray foodBank = new JSONArray();

        JSONObject lentils = new JSONObject();
        JSONObject fg_lentils = new JSONObject();
        fg_lentils.put(FoodLiterals.LENTILS_MASOOR, FoodLiterals.LENTILS_MASOOR);
        fg_lentils.put(FoodLiterals.LENTILS_MOONG, FoodLiterals.LENTILS_MOONG);
        lentils.put(FoodLiterals.FG_LENTILS, fg_lentils);

        JSONObject veggies = new JSONObject();
        JSONObject fg_veggies = new JSONObject();
        fg_veggies.put(FoodLiterals.VEGGIES_SQUASH, FoodLiterals.VEGGIES_SQUASH);
        fg_veggies.put(FoodLiterals.VEGGIES_ZUCCHINI, FoodLiterals.VEGGIES_ZUCCHINI);
        veggies.put(FoodLiterals.FG_VEGGIES, fg_veggies);

        JSONObject fruits = new JSONObject();
        JSONObject fg_fruits = new JSONObject();
        fg_fruits.put(FoodLiterals.FRUITS_APPLE, FoodLiterals.FRUITS_APPLE);
        fg_fruits.put(FoodLiterals.FRUITS_ORANGE, FoodLiterals.FRUITS_ORANGE);
        fruits.put(FoodLiterals.FG_FRUITS, fg_fruits);

        JSONObject dairy = new JSONObject();
        JSONObject fg_dairy = new JSONObject();
        fg_dairy.put(FoodLiterals.DAIRY_MILK, FoodLiterals.DAIRY_MILK);
        fg_dairy.put(FoodLiterals.DAIRY_YOGURT, FoodLiterals.DAIRY_YOGURT);
        dairy.put(FoodLiterals.FG_DAIRY, fg_dairy);

        JSONObject grains = new JSONObject();
        JSONObject fg_grains = new JSONObject();
        fg_grains.put(FoodLiterals.GRAINS_BREAD, FoodLiterals.GRAINS_BREAD);
        fg_grains.put(FoodLiterals.GRAINS_BROWN_RICE, FoodLiterals.GRAINS_BROWN_RICE);
        grains.put(FoodLiterals.FG_GRAINS, fg_grains);

        foodBank.put(lentils);
        foodBank.put(veggies);
        foodBank.put(fruits);
        foodBank.put(dairy);
        foodBank.put(grains);

        return foodBank;
    }

}
