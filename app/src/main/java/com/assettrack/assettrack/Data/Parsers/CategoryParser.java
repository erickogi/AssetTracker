package com.assettrack.assettrack.Data.Parsers;

import com.assettrack.assettrack.Models.CategoriesModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoryParser {
    public static ArrayList<CategoriesModel> parse(JSONArray response) {
        ArrayList<CategoriesModel> categoriesModels = new ArrayList<>();


        try {

            for (int a = 0; a < response.length(); a++) {
                JSONObject custObject = response.getJSONObject(a);

                CategoriesModel customerModel = new CategoriesModel();
                customerModel.setId(custObject.optInt("id"));
                customerModel.setName(custObject.optString("name"));
                customerModel.setCreated_on(custObject.optString("created_at"));

                categoriesModels.add(customerModel);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return categoriesModels;
    }
}
