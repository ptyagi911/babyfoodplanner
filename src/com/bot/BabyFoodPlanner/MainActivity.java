package com.bot.babyfoodplanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.*;

public class MainActivity extends Activity {
	LaunchView mLaunchView;

    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);

    	setContentView(R.layout.main);
    	
    	Button btnFlavors = (Button) findViewById(R.id.btn_flavors);
    	//btnFlavors.setBackground(getActionShapeDrawable());
    	
    	Button btnMenus = (Button) findViewById(R.id.btn_menus);
    	//btnMenus.setBackground(getActionShapeDrawable());
    }

    public void goToFlavors(View view) {
        Intent intent = new Intent(this, FoodListActivity.class);
        startActivity(intent);
    }

    public void goToMenus(View view) {
        Intent intent = new Intent(this, MenusActivity.class);
        startActivity(intent);
    }

    public ShapeDrawable getActionShapeDrawable() {
    	int x = 0;
        int y = 0;
        int width = 0;
        int height = 0;

        ShapeDrawable sDrawable = new ShapeDrawable(new OvalShape());
        sDrawable.getPaint().setColor(0xff74AC23);
        sDrawable.setBounds(x, y, x + width, y + height);
        
        return sDrawable;
    }
}