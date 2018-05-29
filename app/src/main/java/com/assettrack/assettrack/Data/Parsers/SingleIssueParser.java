package com.assettrack.assettrack.Data.Parsers;

import com.assettrack.assettrack.Models.IssueModel;

import org.json.JSONObject;

public class SingleIssueParser {
    public static IssueModel parse(JSONObject response) {

        IssueModel issueModel = new IssueModel();


        try {
            issueModel.setAsset_code(response.optString("asset_code"));
            issueModel.setAsset_code(response.optString("work_tickets"));
            issueModel.setAsset_code(response.optString("assets_id"));
            issueModel.setAsset_code(response.optString("startdate"));
            issueModel.setAsset_code(response.optString("closedate"));
            issueModel.setAsset_code(response.optString("nextdueservice"));
            issueModel.setAsset_code(response.optString("travel_hours"));
            issueModel.setAsset_code(response.optString("labour_hours"));
            issueModel.setAsset_code(response.optString("failure_desc"));
            issueModel.setAsset_code(response.optString("failure_soln"));
            issueModel.setAsset_code(response.optString("issue_status"));
            issueModel.setAsset_code(response.optString("safety"));
            issueModel.setAsset_code(response.optString("engineer_id"));
            issueModel.setAsset_code(response.optString("engineer_comment"));
            issueModel.setAsset_code(response.optString("customers_id"));
            issueModel.setAsset_code(response.optString("customer_comment"));
            issueModel.setAsset_code(response.optString("created_at"));
            issueModel.setAsset_code(response.optString("updated_at"));
            issueModel.setAsset_code(response.optString("statename"));
            issueModel.setAsset_code(response.optString("engineername"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return issueModel;
    }
}
