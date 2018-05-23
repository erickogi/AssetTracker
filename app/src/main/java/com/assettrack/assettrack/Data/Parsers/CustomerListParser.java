package com.assettrack.assettrack.Data.Parsers;

import com.assettrack.assettrack.Models.CustomerModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CustomerListParser {
    public static ArrayList<CustomerModel> parse(JSONArray response) {
        ArrayList<CustomerModel> customerModels = new ArrayList<>();

        try {

            for (int a = 0; a < response.length(); a++) {
                JSONObject custObject = response.getJSONObject(a);

                CustomerModel customerModel = new CustomerModel();
                customerModel.setId(custObject.getInt("lable"));
                customerModel.setName(custObject.getString("value"));
                customerModel.setTelephone("");
                customerModel.setPhysical_address("");
                customerModel.setCreated_at("");
                customerModel.setUpdated_at("");

                customerModels.add(customerModel);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return customerModels;
    }

}
