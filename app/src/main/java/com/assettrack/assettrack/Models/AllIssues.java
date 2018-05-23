package com.assettrack.assettrack.Models;

public class AllIssues {

    private IssueModel issueModel;
    private AssetModel assetModel;
    private EngineerModel engineerModel;
    private CustomerModel customerModel;

    public IssueModel getIssueModel() {
        return issueModel;
    }

    public void setIssueModel(IssueModel issueModel) {
        this.issueModel = issueModel;
    }

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
}
