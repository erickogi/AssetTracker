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
                customerModel.setId(custObject.getInt("id"));
                customerModel.setAddress(custObject.getString("address"));
                customerModel.setName(custObject.getString("name"));
                customerModel.setTelephone(custObject.getString("telephone"));
                customerModel.setPhysical_address(custObject.getString("physical_address"));
                customerModel.setCreated_at(custObject.getString("created_at"));
                customerModel.setUpdated_at(custObject.getString("updated_at"));

                customerModels.add(customerModel);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return customerModels;
    }
}
