package com.assettrack.assettrack.Data.Parsers;

import com.assettrack.assettrack.Models.AssetModel;
import com.assettrack.assettrack.Models.CustomerModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
            customerModel.setAssetCount(response.optInt("assetcount"));


            try {
                AssetModel assetModel = new AssetModel();
                JSONArray object = response.optJSONArray("custdesc");
                if (object!=null){
                    ArrayList<AssetModel> assetModels=AssetParser.parse(object);
                    customerModel.setAssetModels(assetModels);
                }

            }catch (Exception nm){
                nm.printStackTrace();
            }





        } catch (JSONException e) {
            e.printStackTrace();
        }

        return customerModel;
    }
}
