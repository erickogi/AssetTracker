package com.assettrack.assettrack.Data.Parsers;

import com.assettrack.assettrack.Models.AssetModel;
import com.assettrack.assettrack.Models.CustomerModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SIngleCustomerParser {
    public static CustomerModel parse(JSONObject response) {


        CustomerModel customerModel = new CustomerModel();

        try {


            customerModel.setId(response.optInt("id"));
            customerModel.setAddress(response.optString("address"));
            customerModel.setName(response.optString("name"));
            customerModel.setTelephone(response.optString("telephone"));
            customerModel.setPhysical_address(response.optString("physical_address"));
            customerModel.setCreated_at(response.optString("created_at"));
            customerModel.setUpdated_at(response.optString("updated_at"));
            customerModel.setAssetCount(response.optInt("assetcount"));


            try {
                AssetModel assetModel = new AssetModel();
                JSONArray object = response.optJSONArray("custdesc");
                if (object!=null){
                    ArrayList<AssetModel> assetModels = AssetParser.parse1(object);
                    customerModel.setAssetModels(assetModels);
                }

            }catch (Exception nm){
                nm.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return customerModel;
    }
}
