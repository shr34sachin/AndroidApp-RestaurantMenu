package com.sachin.sachinshrestha.restaurantmenu;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by SachinShrestha on 6/16/2016.
 */
public class FoodItemsAdapter extends ArrayAdapter<FoodItems> {
    private Context context;
    private List<FoodItems> foodItemsList;

    public FoodItemsAdapter(Context context, int resource, List<FoodItems> objects) {
        super(context, resource, objects);
        this.context = context;
        this.foodItemsList = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.items_food, parent, false);

        FoodItems foodItems = foodItemsList.get(position);
        //Display name in the TextView widget
        TextView tvName = (TextView) view.findViewById(R.id.tvFoodName);
        tvName.setText("Item Name: " + foodItems.getName());

        //Display price in the TextView widget
        TextView tvPrice = (TextView) view.findViewById(R.id.tvFoodPrice);
        tvPrice.setText("Price: $ " + String.valueOf(foodItems.getPrice()));

        // display photo in ImageView widget
        ImageView image = (ImageView) view.findViewById(R.id.ivFoodItem);
        image.setImageBitmap(foodItems.getBitmap());

        return view;
    }
}
