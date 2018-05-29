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
//
//            "id": 14,
//                    "asset_name": "wheelbarrow",
//                    "asset_code": "8901072002478",
//                    "asset_image": "15b0d61ba7ebee.png",
//                    "state": null,
//                    "warranty": "No",
//                    "warranty_duration": null,
//                    "model": "tredt",
//                    "serial": "345565",
//                    "manufacturer": "bg manufacturing",
//                    "yr_of_manufacture": 2013,
//                    "nextservicedate": null,
//                    "customers_id": 5,

            assetModel.setId(response.optInt("id"));
            assetModel.setAsset_name(response.optString("asset_name"));
            assetModel.setAsset_code(response.optString("asset_code"));
            assetModel.setAsset_image(response.optString("asset_image"));
            assetModel.setState(response.optInt("state"));
            assetModel.setWarranty(response.optString("warranty"));
            assetModel.setWarranty_duration(response.optString("warranty_duration"));
            assetModel.setModel(response.optString("model"));
            assetModel.setSerial(response.optString("serial"));
            assetModel.setManufacturer(response.optString("manufacturer"));
            assetModel.setYr_of_manufacture(response.optString("yr_of_manufacture"));
            assetModel.setNextservicedate(response.optString("nextservicedate"));
            assetModel.setCustomers_id(response.optString("customers_id"));
            assetModel.setContact_person_position(response.optString("contact_person_position"));
            assetModel.setDepartment(response.optString("department"));
            assetModel.setRoomsizestatus(response.optString("roomsizestatus"));
            assetModel.setRoom_meets_specification(response.optString("room_meets_specification"));
            assetModel.setRoom_explanation(response.optString("room_explanation"));
            assetModel.setEngineer_id(response.optString("engineer_id"));
            assetModel.setTrainees(response.optString("trainees"));
            assetModel.setTrainee_position(response.optString("trainee_position"));
            assetModel.setEngineer_remarks(response.optString("engineer_remarks"));
            assetModel.setInstallation_date(response.optString("installation_date"));
            assetModel.setRecieversDate(response.optString("recieversDate"));
            assetModel.setRecievers_name(response.optString("recievers_name"));
            assetModel.setReceiver_designation(response.optString("receiver_designation"));
            assetModel.setReceiver_comments(response.optString("receiver_comments"));
            assetModel.setCreated_at(response.optString("created_at"));
            assetModel.setUpdated_at(response.optString("updated_at"));
            assetModel.setStatename(response.optString("statename"));
            assetModel.setNextservice(response.optString("nextservice"));

            ArrayList<Parts> partsArrayList = new ArrayList<>();

            JSONArray parts = response.getJSONArray("accdesc");
            for (int b = 0; b < parts.length(); b++) {
                JSONObject jsonPart = parts.getJSONObject(b);
                Parts part = new Parts();
                part.setAssets_id(jsonPart.optString("assets_id"));
                part.setId(jsonPart.optString("id"));
                part.setCreated_at(jsonPart.optString("created_at"));
                part.setUpdated_at(jsonPart.optString("updated_at"));

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
