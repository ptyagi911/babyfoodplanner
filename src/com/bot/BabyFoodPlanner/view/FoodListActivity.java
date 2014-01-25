package com.bot.babyfoodplanner.view;

import org.json.JSONException;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bot.babyfoodplanner.R;
import com.bot.babyfoodplanner.model.FoodManager;

public class FoodListActivity extends ExpandableListActivity implements SearchView.OnQueryTextListener {

    TextView mSearchText;
    int mSortMode = -1;

    ExpandableListAdapter mAdapter;
    private static FoodManager mFoodManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setup search text box
        mSearchText = new TextView(this);
        mFoodManager = FoodManager.getInstance(getApplicationContext());

        // Set up our adapter
//        mAdapter = new MyExpandableListAdapter();
//        setListAdapter(mAdapter);
//        registerForContextMenu(getExpandableListView());
    }

    public static FoodManager getFoodManager() {
    	return mFoodManager;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actions, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mSortMode != -1) {
            Drawable icon = menu.findItem(mSortMode).getIcon();
            menu.findItem(R.id.action_sort).setIcon(icon);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, "Selected item: " + item.getTitle() +
                ", Id: " + item.getItemId(), Toast.LENGTH_SHORT).show();

        switch (item.getItemId()) {
            case R.id.action_add:
                Toast.makeText(this, "Add new item", Toast.LENGTH_LONG).show();
                Intent foodEditorIntent = new Intent(this, FoodEditorActivity.class);
                startActivity(foodEditorIntent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    // This method is specified as an onClick handler in the menu xml and will
    // take precedence over the Activity's onOptionsItemSelected method.
    // See res/menu/actions.xml for more info.
    public void onSort(MenuItem item) {
        mSortMode = item.getItemId();
        // Request a call to onPrepareOptionsMenu so we can change the sort icon
        invalidateOptionsMenu();
    }

    // The following callbacks are called for the SearchView.OnQueryChangeListener
    // For more about using SearchView, see src/.../view/SearchView1.java and SearchView2.java
    public boolean onQueryTextChange(String newText) {
        newText = newText.isEmpty() ? "" : "Query so far: " + newText;
        mSearchText.setText(newText);
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(this, "Searching for: " + query + "...", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Test menu");
        menu.add(0, 0, 0, "Test Action");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo) item.getMenuInfo();

        String title = ((TextView) info.targetView).getText().toString();

        int type = ExpandableListView.getPackedPositionType(info.packedPosition);
        if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
            int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition);
            int childPos = ExpandableListView.getPackedPositionChild(info.packedPosition);
            Toast.makeText(this, title + ": Child " + childPos + " clicked in group " + groupPos,
                    Toast.LENGTH_SHORT).show();
            return true;
        } else if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
            int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition);
            Toast.makeText(this, title + ": Group " + groupPos + " clicked", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }

    //Activity lifecycle 
    public void onStop() {
    	Log.d("Priyanka", "OnStop is called");
    	super.onStop();
    }
    
    public void onResume() {
    	Log.d("Priyanka", "OnResume is called");
    	mAdapter = new MyExpandableListAdapter();
        setListAdapter(mAdapter);
        registerForContextMenu(getExpandableListView());
    	super.onResume();
    }
    
    public void onPause() {
    	Log.d("Priyanka", "OnPause is called");
    	unregisterForContextMenu(getExpandableListView());
    	super.onPause();
    }
    
    public void onRestart() {
    	Log.d("Priyanka", "onRestart is called");
    	super.onRestart();
    }
    
    /**
     * A simple adapter which maintains an ArrayList of photo resource Ids.
     * Each photo is displayed as an image. This adapter supports clearing the
     * list of photos and adding a new photo.
     *
     */
    public class MyExpandableListAdapter extends BaseExpandableListAdapter {

        private String[] foodGroups;
        private String[][] foodNames;

        public MyExpandableListAdapter() {
        	foodGroups =  getFoodGroups();
            foodNames = getFoodNames();
            Log.d("Priyanka", "MyExpandableListAdapter is called." +
            		"Last FG: " + foodGroups[foodGroups.length -1]);
        }
        
        //Query from database to get food families
        public String[] getFoodGroups() {
            try {
                mFoodManager.parseFoodGroups();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String[] foodGroups = mFoodManager.getFoodGroups();

            return foodGroups;
        }

        //Query from database to get food families
        public String[][] getFoodNames() {
            String[][] foodNames = mFoodManager.getFoodNames();
            return foodNames;
        }

        public Object getChild(int groupPosition, int childPosition) {
            return foodNames[groupPosition][childPosition];
        }

        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        public int getChildrenCount(int groupPosition) {
            return foodNames[groupPosition].length;
        }

        public TextView getGenericView() {
            // Layout parameters for the ExpandableListView
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 64);

            TextView textView = new TextView(FoodListActivity.this);
            textView.setLayoutParams(lp);
            // Center the text vertically
            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            // Set the text starting position
            textView.setPadding(36, 0, 0, 0);
            return textView;
        }

        public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                                 View convertView, ViewGroup parent) {
            TextView textView = getGenericView();
            textView.setText(getChild(groupPosition, childPosition).toString());
            return textView;
        }

        public Object getGroup(int groupPosition) {
            return foodGroups[groupPosition];
        }

        public int getGroupCount() {
            return foodGroups.length;
        }

        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                                 ViewGroup parent) {
            TextView textView = getGenericView();
            textView.setText(getGroup(groupPosition).toString());
            return textView;
        }

        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        public boolean hasStableIds() {
            return true;
        }

    }

}
