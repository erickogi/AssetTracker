package com.assettrack.assettrack.Data.Parsers;

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
                assetList.setCode(object.getString("code"));
                assetList.setLable(object.getString("lable"));
                assetList.setValue(object.getString("value"));
                assetList.setCustomers(object.getString("customers"));

                JSONObject custObject = new JSONObject("custdesc");
                CustomerModel customerModel = new CustomerModel();
                customerModel.setId(custObject.getInt("id"));
                customerModel.setAddress(custObject.getString("address"));
                customerModel.setName(custObject.getString("name"));
                customerModel.setTelephone(custObject.getString("telephone"));
                customerModel.setPhysical_address(custObject.getString("physical_address"));
                customerModel.setCreated_at(custObject.getString("created_at"));
                customerModel.setUpdated_at(custObject.getString("updated_at"));


                assetList.setCustomerModel(customerModel);

                assetLists.add(assetList);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return assetLists;
    }
}
