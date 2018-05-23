package com.assettrack.assettrack.Data.Parsers;

import com.assettrack.assettrack.Models.AssetModel;
import com.assettrack.assettrack.Models.Parts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AssetParser {

    public static ArrayList<AssetModel> parse(JSONArray response) {


        ArrayList<AssetModel> assetModels = new ArrayList<>();
        try {

            for (int a = 0; a < response.length(); a++) {
                AssetModel assetModel = new AssetModel();
                JSONObject object = response.getJSONObject(a);


                assetModel.setId(object.optInt("id"));
                assetModel.setAsset_name(object.optString("asset_name"));
                assetModel.setAsset_code(object.optString("asset_code"));
                assetModel.setAsset_image(object.optString("asset_image"));
                assetModel.setState(object.optInt("state"));
                assetModel.setWarranty(object.optString("warranty"));
                assetModel.setWarranty_duration(object.optString("warranty_duration"));
                assetModel.setModel(object.optString("model"));
                assetModel.setSerial(object.optString("serial"));
                assetModel.setManufacturer(object.optString("manufacturer"));
                assetModel.setYr_of_manufacture(object.optString("yr_of_manufacture"));
                assetModel.setNextservicedate(object.optString("nextservicedate"));
                assetModel.setCustomers_id(object.optString("customers_id"));
                assetModel.setContact_person_position(object.optString("contact_person_position"));
                assetModel.setDepartment(object.optString("department"));
                assetModel.setRoomsizestatus(object.optString("roomsizestatus"));
                assetModel.setRoom_meets_specification(object.optString("room_meets_specification"));
                assetModel.setRoom_explanation(object.optString("room_explanation"));
                assetModel.setEngineer_id(object.optString("engineer_id"));
                assetModel.setTrainees(object.optString("trainees"));
                assetModel.setTrainee_position(object.optString("trainee_position"));
                assetModel.setEngineer_remarks(object.optString("engineer_remarks"));
                assetModel.setInstallation_date(object.optString("installation_date"));
                assetModel.setRecieversDate(object.optString("recieversDate"));
                assetModel.setRecievers_name(object.optString("recievers_name"));
                assetModel.setReceiver_designation(object.optString("receiver_designation"));
                assetModel.setReceiver_comments(object.optString("receiver_comments"));
                assetModel.setCreated_at(object.optString("created_at"));
                assetModel.setUpdated_at(object.optString("updated_at"));
                assetModel.setStatename(object.optString("statename"));
                assetModel.setNextservice(object.optString("nextservice"));

                ArrayList<Parts> partsArrayList = new ArrayList<>();

                try {
                    JSONArray parts = object.optJSONArray("accdesc");
                    for (int b = 0; b < parts.length(); b++) {
                        JSONObject jsonPart = parts.getJSONObject(b);
                        Parts part = new Parts();
                        part.setAssets_id(jsonPart.optString("assets_id"));
                        part.setId(jsonPart.optString("id"));
                        part.setCreated_at(jsonPart.optString("created_at"));
                        part.setUpdated_at(jsonPart.optString("updated_at"));

                        partsArrayList.add(part);


                    }
                } catch (Exception b) {
                    b.printStackTrace();
                }

                assetModel.setParts(partsArrayList);

                assetModels.add(assetModel);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return assetModels;
    }
}
