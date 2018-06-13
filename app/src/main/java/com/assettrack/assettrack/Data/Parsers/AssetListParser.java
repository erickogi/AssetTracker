package com.assettrack.assettrack.Data.Parsers;

import android.util.Log;

import com.assettrack.assettrack.Models.AssetListModel;
import com.assettrack.assettrack.Models.CustomerModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AssetListParser {


    public static ArrayList<AssetListModel> parse(JSONArray response) {

        ArrayList<AssetListModel> assetLists = new ArrayList<>();
        try {
            for (int a = 0; a < response.length(); a++) {
                AssetListModel assetList = new AssetListModel();
                JSONObject object = response.getJSONObject(a);
                assetList.setCode(object.optString("code"));
                assetList.setLable(object.optString("lable"));
                assetList.setValue(object.optString("value"));
                assetList.setCustomers(object.optString("customers"));

                JSONObject custObject = new JSONObject(object.optString("custdesc"));
                CustomerModel customerModel = new CustomerModel();
                customerModel.setId(custObject.optInt("id"));
                customerModel.setAddress(custObject.optString("address"));
                customerModel.setName(custObject.optString("name"));
                customerModel.setTelephone(custObject.optString("telephone"));
                customerModel.setPhysical_address(custObject.optString("physical_address"));
                customerModel.setCreated_at(custObject.optString("created_at"));
                customerModel.setUpdated_at(custObject.optString("updated_at"));


                assetList.setCustomerModel(customerModel);

                assetLists.add(assetList);

            }
        } catch (JSONException e) {
            Log.d("getDataAss", e.toString());

            e.printStackTrace();
        }

        return assetLists;
    }

    public static ArrayList<AssetListModel> parseList(JSONArray response) {

        ArrayList<AssetListModel> assetLists = new ArrayList<>();
        try {
            for (int a = 0; a < response.length(); a++) {
                AssetListModel assetList = new AssetListModel();
                JSONObject object = response.getJSONObject(a);
                assetList.setLable(object.optString("lable"));
                assetList.setValue(object.optString("value"));

                assetLists.add(assetList);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return assetLists;
    }
}
