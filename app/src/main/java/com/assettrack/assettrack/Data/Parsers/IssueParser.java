package com.assettrack.assettrack.Data.Parsers;

import com.assettrack.assettrack.Models.IssueModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class IssueParser {

    public static ArrayList<IssueModel> parse(JSONArray response) {
        ArrayList<IssueModel> issueModels = new ArrayList<>();

        try {
            for (int a = 0; a < response.length(); a++) {
                JSONObject object = response.getJSONObject(a);

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
                issueModel.setIssue_status(object.optString("issue_status"));
                issueModel.setSafety(object.optString("safety"));
                issueModel.setEngineer_id(object.optString("engineer_id"));
                issueModel.setEngineer_comment(object.optString("engineer_comment"));
                issueModel.setCustomers_id(object.optString("customers_id"));
                issueModel.setCustomer_comment(object.optString("customer_comment"));
                issueModel.setCreated_at(object.optString("created_at"));
                issueModel.setUpdated_at(object.optString("updated_at"));
                issueModel.setStatename(object.optString("statename"));
                issueModel.setEngineername(object.optString("engineername"));


                issueModels.add(issueModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return issueModels;
    }
}
