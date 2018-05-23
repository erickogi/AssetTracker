package com.assettrack.assettrack.Data.Parsers;

import com.assettrack.assettrack.Models.IssueModel;

import org.json.JSONException;
import org.json.JSONObject;

public class SingleIssueParser {
    public static IssueModel parse(JSONObject response) {

        IssueModel issueModel = new IssueModel();


        try {
            issueModel.setAsset_code(response.getString("asset_code"));
            issueModel.setAsset_code(response.getString("work_tickets"));
            issueModel.setAsset_code(response.getString("assets_id"));
            issueModel.setAsset_code(response.getString("startdate"));
            issueModel.setAsset_code(response.getString("closedate"));
            issueModel.setAsset_code(response.getString("nextdueservice"));
            issueModel.setAsset_code(response.getString("travel_hours"));
            issueModel.setAsset_code(response.getString("labour_hours"));
            issueModel.setAsset_code(response.getString("failure_desc"));
            issueModel.setAsset_code(response.getString("failure_soln"));
            issueModel.setAsset_code(response.getString("issue_status"));
            issueModel.setAsset_code(response.getString("safety"));
            issueModel.setAsset_code(response.getString("engineer_id"));
            issueModel.setAsset_code(response.getString("engineer_comment"));
            issueModel.setAsset_code(response.getString("customers_id"));
            issueModel.setAsset_code(response.getString("customer_comment"));
            issueModel.setAsset_code(response.getString("created_at"));
            issueModel.setAsset_code(response.getString("updated_at"));
            issueModel.setAsset_code(response.getString("statename"));
            issueModel.setAsset_code(response.getString("engineername"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return issueModel;
    }
}
