package com.assettrack.assettrack.Data.Parsers;

import com.assettrack.assettrack.Models.EngineerModel;

import org.json.JSONObject;

public class EngineerParser {

    public static EngineerModel parse(JSONObject response) {
        EngineerModel engineerModel = new EngineerModel();


        try {
            engineerModel.setId(response.optInt("id"));
            engineerModel.setFirstname(response.optString("firstname"));
            engineerModel.setLastname(response.optString("lastname"));
            engineerModel.setEmployeeid(response.optString("employeeid"));
            engineerModel.setRole(response.optInt("role"));
            engineerModel.setEmail(response.optString("email"));
            engineerModel.setPhoneNumber(response.optString("phoneNumber"));
            engineerModel.setDesignation(response.optString("designation"));
            engineerModel.setCreated_at(response.optString("created_at"));
            engineerModel.setUpdated_at(response.optString("updated_at"));
            engineerModel.setFull_name(response.optString("full_name"));
            engineerModel.setRolename(response.optString("rolename"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return engineerModel;
    }
}
