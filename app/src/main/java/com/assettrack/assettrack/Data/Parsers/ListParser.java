package com.assettrack.assettrack.Data.Parsers;

import com.assettrack.assettrack.Models.ListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListParser {


    public static ArrayList<ListModel> parse(JSONArray response) {

        ArrayList<ListModel> listModel = new ArrayList<>();
        try {
            for (int a = 0; a < response.length(); a++) {
                ListModel assetList = new ListModel();
                JSONObject object = response.getJSONObject(a);
                assetList.setLabel(object.optString("lable"));
                assetList.setValue(object.optString("value"));

                listModel.add(assetList);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return listModel;
    }


}
