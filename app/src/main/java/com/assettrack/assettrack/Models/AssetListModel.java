package com.assettrack.assettrack.Models;

public class AssetListModel {

    private String lable;
    private String value;
    private String code;
    private String customers;
    private String custdesc;
    private CustomerModel customerModels;

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCustomers() {
        return customers;
    }

    public void setCustomers(String customers) {
        this.customers = customers;
    }

    public String getCustdesc() {
        return custdesc;
    }

    public void setCustdesc(String custdesc) {
        this.custdesc = custdesc;
    }

    public CustomerModel getCustomerModel() {
        return customerModels;
    }

    public void setCustomerModel(CustomerModel customerModels) {
        this.customerModels = customerModels;
    }
}
