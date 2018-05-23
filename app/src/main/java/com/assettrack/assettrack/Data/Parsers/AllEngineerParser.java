package com.assettrack.assettrack.Data.Parsers;

import com.assettrack.assettrack.Models.EngineerModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllEngineerParser {

    public static ArrayList<EngineerModel> parse(JSONArray response) {

        ArrayList<EngineerModel> engineerModels = new ArrayList<>();
        try {
            for (int a = 0; a < response.length(); a++) {
                JSONObject object = response.getJSONObject(a);

                EngineerModel engineerModel = new EngineerModel();


                engineerModel.setId(object.optInt("id"));
                engineerModel.setFirstname(object.optString("firstname"));
                engineerModel.setLastname(object.optString("lastname"));
                engineerModel.setEmployeeid(object.optString("employeeid"));
                engineerModel.setRole(object.optInt("role"));
                engineerModel.setEmail(object.optString("email"));
                engineerModel.setPhoneNumber(object.optString("phoneNumber"));
                engineerModel.setDesignation(object.optString("designation"));
                engineerModel.setCreated_at(object.optString("created_at"));
                engineerModel.setUpdated_at(object.optString("updated_at"));
                engineerModel.setFull_name(object.optString("full_name"));
                engineerModel.setRolename(object.optString("rolename"));

                engineerModels.add(engineerModel);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return engineerModels;
    }
}
