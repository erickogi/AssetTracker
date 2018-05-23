package com.assettrack.assettrack.Data.Parsers;

import com.assettrack.assettrack.Models.CustomerModel;

import org.json.JSONException;
import org.json.JSONObject;

public class SIngleCustomerParser {
    public static CustomerModel parse(JSONObject response) {


        CustomerModel customerModel = new CustomerModel();

        try {


            customerModel.setId(response.getInt("id"));
            customerModel.setAddress(response.getString("address"));
            customerModel.setName(response.getString("name"));
            customerModel.setTelephone(response.getString("telephone"));
            customerModel.setPhysical_address(response.getString("physical_address"));
            customerModel.setCreated_at(response.getString("created_at"));
            customerModel.setUpdated_at(response.getString("updated_at"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return customerModel;
    }
}
