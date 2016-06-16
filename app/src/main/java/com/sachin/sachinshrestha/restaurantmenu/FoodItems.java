package com.sachin.sachinshrestha.restaurantmenu;

import android.graphics.Bitmap;

/**
 * Created by SachinShrestha on 6/16/2016.
 */
public class FoodItems {
    private int productId;
    private String name;
    private String category;
    private double Price;
    private String Photo;
    private Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }


    //setter method, using Generate
    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }

    public void setPhoto(String Photo) {
        this.Photo = Photo;
    }

    //getter method, using Generate
    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return Price;
    }

    public String getPhoto() {
        return Photo;
    }
}
