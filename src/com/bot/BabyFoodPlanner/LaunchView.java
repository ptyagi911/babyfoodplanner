package com.bot.babyfoodplanner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.View;

class LaunchView extends View {
    private ShapeDrawable mDrawable;

    public LaunchView(Context context) {
    	super(context);
    	createView();
    }

    public LaunchView(Context context, AttributeSet attribSet) {
    	super(context, attribSet);
    	createView();
    }

    private void createView() {
    	int x = 10;
        int y = 10;
        int width = 300;
        int height = 50;

        mDrawable = new ShapeDrawable(new OvalShape());
        mDrawable.getPaint().setColor(0xff74AC23);
        mDrawable.setBounds(x, y, x + width, y + height);
    }
    
    protected void onDraw(Canvas canvas) {
    	mDrawable.draw(canvas);
    }
}