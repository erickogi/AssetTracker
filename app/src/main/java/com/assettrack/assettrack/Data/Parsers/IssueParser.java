package com.assettrack.assettrack.Data.Parsers;

import com.assettrack.assettrack.Models.AssetModel;
import com.assettrack.assettrack.Models.CustomerModel;
import com.assettrack.assettrack.Models.EngineerModel;
import com.assettrack.assettrack.Models.IssueModel;
import com.assettrack.assettrack.Models.Parts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class IssueParser {

    public static ArrayList<IssueModel> parse(JSONArray response) {
        ArrayList<IssueModel> issueModels = new ArrayList<>();

        try {
            for (int a = 0; a < response.length(); a++) {
                JSONObject object = response.optJSONObject(a);
                IssueModel issueModel = new IssueModel();
                issueModel.setAsset_code(object.optString("asset_code"));
                issueModel.setWork_tickets(object.optString("work_tickets"));
                issueModel.setAssets_id(object.optString("assets_id"));
                issueModel.setStartdate(object.optString("startdate"));
                issueModel.setClosedate(object.optString("closedate"));
                issueModel.setNextdueservice(object.optString("nextdueservice"));
                issueModel.setTravel_hours(object.optString("travel_hours"));
                issueModel.setLabour_hours(object.optString("labour_hours"));
                issueModel.setFailure_desc(object.optString("failure_desc"));
                issueModel.setFailure_soln(object.optString("failure_soln"));
                issueModel.setIssue_status(object.optInt("issue_status"));
                issueModel.setSafety(object.optString("safety"));
                issueModel.setEngineer_id(object.optString("engineer_id"));
                issueModel.setEngineer_comment(object.optString("engineer_comment"));
                issueModel.setCustomers_id(object.optString("customers_id"));
                issueModel.setCustomer_comment(object.optString("customer_comment"));
                issueModel.setCreated_at(object.optString("created_at"));
                issueModel.setUpdated_at(object.optString("updated_at"));
                issueModel.setStatename(object.optString("statename"));
                issueModel.setEngineername(object.optString("engineername"));


                EngineerModel engineerModel=new EngineerModel();

                try{
                    JSONObject jsonEngineer=object.optJSONObject("engdesc");
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




                }catch (Exception nm){
                    nm.printStackTrace();
                }
                issueModel.setEngineerModel(engineerModel);

                CustomerModel customerModel=new CustomerModel();
                JSONObject jsonCustomer=object.optJSONObject("cusdesc");

                try{
                    if(jsonCustomer!=null){
                        customerModel.setId(jsonCustomer.optInt("id"));
                        customerModel.setAddress(jsonCustomer.optString("address"));
                        customerModel.setName(jsonCustomer.optString("name"));
                        customerModel.setTelephone(jsonCustomer.optString("telephone"));
                        customerModel.setPhysical_address(jsonCustomer.optString("physical_address"));
                        customerModel.setCreated_at(jsonCustomer.optString("created_at"));
                        customerModel.setUpdated_at(jsonCustomer.optString("updated_at"));

                    }

                }catch (Exception nm){
                    nm.printStackTrace();
                }
                issueModel.setCustomerModel(customerModel);

                AssetModel assetModel=new AssetModel();
                JSONObject jsonAsset=object.optJSONObject("assdesc");
                try{
                    if(jsonAsset!=null){


                        assetModel.setId(jsonAsset.optInt("id"));
                        assetModel.setAsset_name(jsonAsset.optString("asset_name"));
                        assetModel.setAsset_code(jsonAsset.optString("asset_code"));
                        assetModel.setAsset_image(jsonAsset.optString("asset_image"));
                        assetModel.setState(jsonAsset.optInt("state"));
                        assetModel.setWarranty(jsonAsset.optString("warranty"));
                        assetModel.setWarranty_duration(jsonAsset.optString("warranty_duration"));
                        assetModel.setModel(jsonAsset.optString("model"));
                        assetModel.setSerial(jsonAsset.optString("serial"));
                        assetModel.setManufacturer(jsonAsset.optString("manufacturer"));
                        assetModel.setYr_of_manufacture(jsonAsset.optString("yr_of_manufacture"));
                        assetModel.setNextservicedate(jsonAsset.optString("nextservicedate"));
                        assetModel.setCustomers_id(jsonAsset.optString("customers_id"));
                        assetModel.setContact_person_position(jsonAsset.optString("contact_person_position"));
                        assetModel.setDepartment(jsonAsset.optString("department"));
                        assetModel.setRoomsizestatus(jsonAsset.optString("roomsizestatus"));
                        assetModel.setRoom_meets_specification(jsonAsset.optString("room_meets_specification"));
                        assetModel.setRoom_explanation(jsonAsset.optString("room_explanation"));
                        assetModel.setEngineer_id(jsonAsset.optString("engineer_id"));
                        assetModel.setTrainees(jsonAsset.optString("trainees"));
                        assetModel.setTrainee_position(jsonAsset.optString("trainee_position"));
                        assetModel.setEngineer_remarks(jsonAsset.optString("engineer_remarks"));
                        assetModel.setInstallation_date(jsonAsset.optString("installation_date"));
                        assetModel.setRecieversDate(jsonAsset.optString("recieversDate"));
                        assetModel.setRecievers_name(jsonAsset.optString("recievers_name"));
                        assetModel.setReceiver_designation(jsonAsset.optString("receiver_designation"));
                        assetModel.setReceiver_comments(jsonAsset.optString("receiver_comments"));
                        assetModel.setCreated_at(jsonAsset.optString("created_at"));
                        assetModel.setUpdated_at(jsonAsset.optString("updated_at"));
                        assetModel.setStatename(jsonAsset.optString("statename"));
                        assetModel.setNextservice(jsonAsset.optString("nextservice"));

                        ArrayList<Parts> partsArrayList = new ArrayList<>();

                        JSONArray parts = jsonAsset.optJSONArray("accdesc");
                        for (int b = 0; b < parts.length(); b++) {
                            JSONObject jsonPart = parts.optJSONObject(b);
                            Parts part = new Parts();
                            part.setAssets_id(jsonPart.optString("assets_id"));
                            part.setId(jsonPart.optString("id"));
                            part.setDescription(jsonPart.optString("description"));
                            part.setCreated_at(jsonPart.optString("created_at"));
                            part.setUpdated_at(jsonPart.optString("updated_at"));

                            partsArrayList.add(part);


                        }

                        assetModel.setParts(partsArrayList);

                    }



                }catch (Exception nm){
                    nm.printStackTrace();
                }
                issueModel.setAssetModel(assetModel);


                issueModels.add(issueModel);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return issueModels;
    }
}
