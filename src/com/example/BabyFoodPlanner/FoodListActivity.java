package com.example.BabyFoodPlanner;

import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

public class FoodListActivity extends ExpandableListActivity {

    ExpandableListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up our adapter
        mAdapter = new MyExpandableListAdapter();
        setListAdapter(mAdapter);
        registerForContextMenu(getExpandableListView());
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

    /**
     * A simple adapter which maintains an ArrayList of photo resource Ids.
     * Each photo is displayed as an image. This adapter supports clearing the
     * list of photos and adding a new photo.
     *
     */
    public class MyExpandableListAdapter extends BaseExpandableListAdapter {
        // Sample data set.  foodNames[i] contains the foodNames (String[]) for foodGroups[i].
        private String[] foodGroups = {
                "Lentils",
                "Vegetables",
                "Fruits",
                "Dairy",
                "Grains"
        };

        private String[][] foodNames = {
                {"Moong", "Masoor", "Lobia", "Rajma", "Chana", "Kala Chana", "Moth"},
                {"Zuchini", "Squash", "Carrots", "Sweet Patatos", "Broccoli", "Avocado", "Peas", "Potato"},
                {"Grapes", "Strawberry", "Blueberry", "Banana", "Oranage", "Pear", "Peaches", "Plums"},
                {"Dairy", "Milk", "Cheese", "Tofu", "Yogurt"},
                {"Pasta", "Brown Rice", "BabyFoodMix Parantha"}

        };

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
