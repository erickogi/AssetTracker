package com.assettrack.assettrack.Data.Parsers;

import com.assettrack.assettrack.Models.AssetModel;
import com.assettrack.assettrack.Models.CustomerModel;
import com.assettrack.assettrack.Models.EngineerModel;
import com.assettrack.assettrack.Models.Parts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SingleAssetParser {

    public static AssetModel parse(JSONObject response) {


        AssetModel assetModel = new AssetModel();


        try {


            assetModel.setId(response.getInt("id"));
            assetModel.setAsset_name(response.getString("asset_name"));
            assetModel.setAsset_code(response.getString("asset_code"));
            assetModel.setAsset_image(response.getString("asset_image"));
            assetModel.setState(response.getInt("state"));
            assetModel.setWarranty(response.getString("warranty"));
            assetModel.setWarranty_duration(response.getString("warranty_duration"));
            assetModel.setModel(response.getString("model"));
            assetModel.setSerial(response.getString("serial"));
            assetModel.setManufacturer(response.getString("manufacturer"));
            assetModel.setYr_of_manufacture(response.getString("yr_of_manufacture"));
            assetModel.setNextservicedate(response.getString("nextservicedate"));
            assetModel.setCustomers_id(response.getString("customers_id"));
            assetModel.setContact_person_position(response.getString("contact_person_position"));
            assetModel.setDepartment(response.getString("department"));
            assetModel.setRoomsizestatus(response.getString("roomsizestatus"));
            assetModel.setRoom_meets_specification(response.getString("room_meets_specification"));
            assetModel.setRoom_explanation(response.getString("room_explanation"));
            assetModel.setEngineer_id(response.getString("engineer_id"));
            assetModel.setTrainees(response.getString("trainees"));
            assetModel.setTrainee_position(response.getString("trainee_position"));
            assetModel.setEngineer_remarks(response.getString("engineer_remarks"));
            assetModel.setInstallation_date(response.getString("installation_date"));
            assetModel.setRecieversDate(response.getString("recieversDate"));
            assetModel.setRecievers_name(response.getString("recievers_name"));
            assetModel.setReceiver_designation(response.getString("receiver_designation"));
            assetModel.setReceiver_comments(response.getString("receiver_comments"));
            assetModel.setCreated_at(response.getString("created_at"));
            assetModel.setUpdated_at(response.getString("updated_at"));
            assetModel.setStatename(response.getString("statename"));
            assetModel.setNextservice(response.getString("nextservice"));

            ArrayList<Parts> partsArrayList = new ArrayList<>();

            JSONArray parts = response.getJSONArray("accdesc");
            for (int b = 0; b < parts.length(); b++) {
                JSONObject jsonPart = parts.getJSONObject(b);
                Parts part = new Parts();
                part.setAssets_id(jsonPart.getString("assets_id"));
                part.setId(jsonPart.getString("id"));
                part.setCreated_at(jsonPart.getString("created_at"));
                part.setUpdated_at(jsonPart.getString("updated_at"));

                partsArrayList.add(part);


            }


            EngineerModel engineerModel = new EngineerModel();

            try {
                JSONObject jsonEngineer = response.optJSONObject("engineerdesc");
                engineerModel.setId(jsonEngineer.optInt("id"));
                engineerModel.setFirstname(jsonEngineer.optString("firstname"));
                engineerModel.setLastname(jsonEngineer.optString("lastname"));
                engineerModel.setEmployeeid(jsonEngineer.optString("employeeid"));
                engineerModel.setRole(jsonEngineer.optInt("role"));
                engineerModel.setEmail(jsonEngineer.optString("email"));
                engineerModel.setPhoneNumber(jsonEngineer.optString("phoneNumber"));
                engineerModel.setDesignation(jsonEngineer.optString("designation"));
                engineerModel.setCreated_at(jsonEngineer.optString("created_at"));
                engineerModel.setUpdated_at(jsonEngineer.optString("updated_at"));
                engineerModel.setFull_name(jsonEngineer.optString("full_name"));
                engineerModel.setRolename(jsonEngineer.optString("rolename"));


            } catch (Exception nm) {
                nm.printStackTrace();
            }


            assetModel.setEngineerModel(engineerModel);

            CustomerModel customerModel = new CustomerModel();

            try {
                JSONObject jsonCustomer = response.optJSONObject("custdesc");

                if (jsonCustomer != null) {
                    customerModel.setId(jsonCustomer.optInt("id"));
                    customerModel.setAddress(jsonCustomer.optString("address"));
                    customerModel.setName(jsonCustomer.optString("name"));
                    customerModel.setTelephone(jsonCustomer.optString("telephone"));
                    customerModel.setPhysical_address(jsonCustomer.optString("physical_address"));
                    customerModel.setCreated_at(jsonCustomer.optString("created_at"));
                    customerModel.setUpdated_at(jsonCustomer.optString("updated_at"));

                }

            } catch (Exception nm) {
                nm.printStackTrace();
            }

            assetModel.setCustomerModel(customerModel);







            assetModel.setParts(partsArrayList);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return assetModel;
    }
}
