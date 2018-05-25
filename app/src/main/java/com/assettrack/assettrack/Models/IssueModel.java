package com.assettrack.assettrack.Models;

public class IssueModel {

    private int id;
    private String work_tickets;
    private String assets_id;
    private String asset_code;
    private String startdate;
    private String closedate;
    private String nextdueservice;
    private String travel_hours;
    private String labour_hours;
    private String failure_desc;
    private String failure_soln;
    private String issue_status;
    private String safety;
    private String engineer_id;
    private String engineer_comment;
    private String customers_id;
    private String customer_comment;
    private String created_at;
    private String updated_at;
    private String statename;
    private String engineername;
    private boolean isChecked;

    private AssetModel assetModel;
    private EngineerModel engineerModel;
    private CustomerModel customerModel;

    public AssetModel getAssetModel() {
        return assetModel;
    }

    public void setAssetModel(AssetModel assetModel) {
        this.assetModel = assetModel;
    }

    public EngineerModel getEngineerModel() {
        return engineerModel;
    }

    public void setEngineerModel(EngineerModel engineerModel) {
        this.engineerModel = engineerModel;
    }

    public CustomerModel getCustomerModel() {
        return customerModel;
    }

    public void setCustomerModel(CustomerModel customerModel) {
        this.customerModel = customerModel;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWork_tickets() {
        return work_tickets;
    }

    public void setWork_tickets(String work_tickets) {
        this.work_tickets = work_tickets;
    }

    public String getAssets_id() {
        return assets_id;
    }

    public void setAssets_id(String assets_id) {
        this.assets_id = assets_id;
    }

    public String getAsset_code() {
        return asset_code;
    }

    public void setAsset_code(String asset_code) {
        this.asset_code = asset_code;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getClosedate() {
        return closedate;
    }

    public void setClosedate(String closedate) {
        this.closedate = closedate;
    }

    public String getNextdueservice() {
        return nextdueservice;
    }

    public void setNextdueservice(String nextdueservice) {
        this.nextdueservice = nextdueservice;
    }

    public String getTravel_hours() {
        return travel_hours;
    }

    public void setTravel_hours(String travel_hours) {
        this.travel_hours = travel_hours;
    }

    public String getLabour_hours() {
        return labour_hours;
    }

    public void setLabour_hours(String labour_hours) {
        this.labour_hours = labour_hours;
    }

    public String getFailure_desc() {
        return failure_desc;
    }

    public void setFailure_desc(String failure_desc) {
        this.failure_desc = failure_desc;
    }

    public String getFailure_soln() {
        return failure_soln;
    }

    public void setFailure_soln(String failure_soln) {
        this.failure_soln = failure_soln;
    }

    public String getIssue_status() {
        return issue_status;
    }

    public void setIssue_status(String issue_status) {
        this.issue_status = issue_status;
    }

    public String getSafety() {
        return safety;
    }

    public void setSafety(String safety) {
        this.safety = safety;
    }

    public String getEngineer_id() {
        return engineer_id;
    }

    public void setEngineer_id(String engineer_id) {
        this.engineer_id = engineer_id;
    }

    public String getEngineer_comment() {
        return engineer_comment;
    }

    public void setEngineer_comment(String engineer_comment) {
        this.engineer_comment = engineer_comment;
    }

    public String getCustomers_id() {
        return customers_id;
    }

    public void setCustomers_id(String customers_id) {
        this.customers_id = customers_id;
    }

    public String getCustomer_comment() {
        return customer_comment;
    }

    public void setCustomer_comment(String customer_comment) {
        this.customer_comment = customer_comment;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getStatename() {
        return statename;
    }

    public void setStatename(String statename) {
        this.statename = statename;
    }

    public String getEngineername() {
        return engineername;
    }

    public void setEngineername(String engineername) {
        this.engineername = engineername;
    }
}
