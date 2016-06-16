package com.sachin.sachinshrestha.restaurantmenu;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SachinShrestha on 6/16/2016.
 */
public class XMLParser {
    public static List<FoodItems> parseFeed(String content) {

        try {
            boolean inDataItemTag = false;
            String currentTagName = "";
            FoodItems FoodItems = null;
            List<FoodItems> flowerList = new ArrayList<>();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(content));

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        currentTagName = parser.getName();
                        if (currentTagName.equals("product")) {
                            inDataItemTag = true;
                            FoodItems = new FoodItems();
                            flowerList.add(FoodItems);
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("product")) {
                            inDataItemTag = false;
                        }
                        currentTagName = "";
                        break;

                    case XmlPullParser.TEXT:
                        if (inDataItemTag && FoodItems != null) {
                            switch (currentTagName) {
                                case "productId":
                                    FoodItems.setProductId(Integer.parseInt(parser.getText()));
                                    break;
                                case "name":
                                    FoodItems.setName(parser.getText());
                                    break;
                                case "category":
                                    FoodItems.setCategory(parser.getText());
                                    break;
                                case "price" :
                                    FoodItems.setPrice(Double.parseDouble(parser.getText()));
                                    break;
                                case "photo" :
                                    FoodItems.setPhoto(parser.getText());
                                default:
                                    break;
                            }
                        }
                        break;
                }

                eventType = parser.next();

            }

            return flowerList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
