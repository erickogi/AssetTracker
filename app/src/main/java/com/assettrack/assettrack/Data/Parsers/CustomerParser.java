package com.assettrack.assettrack.Data.Parsers;

import com.assettrack.assettrack.Models.CustomerModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CustomerParser {
    public static ArrayList<CustomerModel> parse(JSONArray response) {
        ArrayList<CustomerModel> customerModels = new ArrayList<>();


        try {

            for (int a = 0; a < response.length(); a++) {
                JSONObject custObject = response.getJSONObject(a);

                CustomerModel customerModel = new CustomerModel();
                customerModel.setId(custObject.optInt("id"));
                customerModel.setAddress(custObject.optString("address"));
                customerModel.setName(custObject.optString("name"));
                customerModel.setTelephone(custObject.optString("telephone"));
                customerModel.setPhysical_address(custObject.optString("physical_address"));
                customerModel.setCreated_at(custObject.optString("created_at"));
                customerModel.setUpdated_at(custObject.optString("updated_at"));
                customerModel.setAssetCount(custObject.optInt("assetcount"));

                customerModels.add(customerModel);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return customerModels;
    }
}
