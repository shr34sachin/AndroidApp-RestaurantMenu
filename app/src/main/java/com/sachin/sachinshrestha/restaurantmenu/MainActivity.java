package com.sachin.sachinshrestha.restaurantmenu;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {
    ProgressBar progressBar;
    Button btnLoad;
    private String uri = "http://sachinshrestha84.netne.net/fooditems.xml";
    private static final String PHOTOS_BASE_URL = 	"http://sachinshrestha84.netne.net/photos/";

    List<FoodItems> foodItemsList;
    List<asyncTasks> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLoad = (Button) findViewById(R.id.btnLoad);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        tasks = new ArrayList<>();

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline()){
                    requestData(uri);
                } else{
                    Toast.makeText(getApplicationContext(),"Network isn't available", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void displayInTextView(){
        FoodItemsAdapter foodItemsAdapter = new FoodItemsAdapter(this,R.layout.items_food,foodItemsList);
        setListAdapter(foodItemsAdapter);
    }

    // check whether the network is availed or not
    // Note that the permissions, ACCESS_NETWORK_STATE and INTERNET should be set first in manifest file
    private boolean isOnline(){
        ConnectivityManager cm= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(netInfo != null && netInfo.isConnectedOrConnecting()){
            return true;
        } else{
            return false;
        }
    }

    private void requestData(String uri) {
        asyncTasks task = new asyncTasks();
        task.execute(uri);
    }

    private class asyncTasks extends AsyncTask<String,String,List<FoodItems>> {

        @Override
        protected void onPreExecute() {
            if (tasks.size() == 0) {
                progressBar.setVisibility(View.VISIBLE);
            }
            tasks.add(this);
        }

        @Override
        protected List<FoodItems> doInBackground(String... params) {
            String content = HttpManager.getData(params[0]);
            foodItemsList = XMLParser.parseFeed(content);

            for(FoodItems foodItems : foodItemsList){
                try{
                    String imageUrl = PHOTOS_BASE_URL + foodItems.getPhoto();
                    InputStream in = (InputStream) new URL(imageUrl).getContent();
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    foodItems.setBitmap(bitmap);
                    in.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            return foodItemsList;
        }

        @Override
        protected void onPostExecute(List<FoodItems> listFromDoInBckGnd) {
            tasks.remove(this);
            if (tasks.size() == 0) {
                progressBar.setVisibility(View.INVISIBLE);
            }
            if (listFromDoInBckGnd == null) {
                Toast.makeText(MainActivity.this, "Web service not available", Toast.LENGTH_LONG).show();
                return;
            }
            foodItemsList = listFromDoInBckGnd;
            displayInTextView();
        }
    }
}
