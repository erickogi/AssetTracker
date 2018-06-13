package com.assettrack.assettrack.Models;

import java.io.Serializable;
import java.util.ArrayList;

import ir.mirrajabi.searchdialog.core.Searchable;

public class AssetModel implements Serializable, Searchable {
    private int id;
    private String asset_name="";
    private String asset_code="";
    private String asset_image="aa";
    private String asset_imageurl = "aa";
    private int state;
    private String warranty="";
    private String warranty_duration="";
    private String model="";
    private String serial="";
    private String manufacturer="";
    private String yr_of_manufacture="";
    private String nextservicedate="";
    private String customers_id="";
    private String contact_person_position="";
    private String department="";
    private String roomsizestatus="";
    private String room_meets_specification="";
    private String room_explanation="";
    private String engineer_id="";
    private String trainees="";
    private String trainee_position="";
    private String engineer_remarks="";
    private String installation_date="";
    private String recieversDate="";
    private String recievers_name="";
    private String receiver_designation = "null";
    private String receiver_comments="";
    private String created_at="";
    private String updated_at="";
    private String statename="";
    private String nextservice="";
    private String lastservicedate="";
    private int category_id;
    private String category = "";

    private String contact_phone;   //ContactPhone
    private String contact_email;  //ContactEmail

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    public String getContact_email() {
        return contact_email;
    }

    public void setContact_email(String contact_email) {
        this.contact_email = contact_email;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    private boolean isChecked;

    private ArrayList<Parts> parts =new ArrayList<>();
    private CustomerModel customerModel=new CustomerModel();
    private EngineerModel engineerModel=new EngineerModel();

    public String getAsset_imageurl() {
        return asset_imageurl;
    }

    public void setAsset_imageurl(String asset_imageurl) {
        this.asset_imageurl = asset_imageurl;
    }

    public String getLastservicedate() {
        return lastservicedate;
    }

    public void setLastservicedate(String lastservicedate) {
        this.lastservicedate = lastservicedate;
    }

    public CustomerModel getCustomerModel() {
        return customerModel;
    }

    public void setCustomerModel(CustomerModel customerModel) {
        this.customerModel = customerModel;
    }

    public EngineerModel getEngineerModel() {
        return engineerModel;
    }

    public void setEngineerModel(EngineerModel engineerModel) {
        this.engineerModel = engineerModel;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public ArrayList<Parts> getParts() {
        return parts;
    }

    public void setParts(ArrayList<Parts> parts) {
        this.parts = parts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAsset_name() {
        return asset_name;
    }

    public void setAsset_name(String asset_name) {
        this.asset_name = asset_name;
    }

    public String getAsset_code() {
        return asset_code;
    }

    public void setAsset_code(String asset_code) {
        this.asset_code = asset_code;
    }

    public String getAsset_image() {
        return asset_image;
    }

    public void setAsset_image(String asset_image) {
        this.asset_image = asset_image;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public String getWarranty_duration() {
        return warranty_duration;
    }

    public void setWarranty_duration(String warranty_duration) {
        this.warranty_duration = warranty_duration;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getYr_of_manufacture() {
        return yr_of_manufacture;
    }

    public void setYr_of_manufacture(String yr_of_manufacture) {
        this.yr_of_manufacture = yr_of_manufacture;
    }

    public String getNextservicedate() {
        return nextservicedate;
    }

    public void setNextservicedate(String nextservicedate) {
        this.nextservicedate = nextservicedate;
    }

    public String getCustomers_id() {
        return customers_id;
    }

    public void setCustomers_id(String customers_id) {
        this.customers_id = customers_id;
    }

    public String getContact_person_position() {
        return contact_person_position;
    }

    public void setContact_person_position(String contact_person_position) {
        this.contact_person_position = contact_person_position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRoomsizestatus() {
        return roomsizestatus;
    }

    public void setRoomsizestatus(String roomsizestatus) {
        this.roomsizestatus = roomsizestatus;
    }

    public String getRoom_meets_specification() {
        return room_meets_specification;
    }

    public void setRoom_meets_specification(String room_meets_specification) {
        this.room_meets_specification = room_meets_specification;
    }

    public String getRoom_explanation() {
        return room_explanation;
    }

    public void setRoom_explanation(String room_explanation) {
        this.room_explanation = room_explanation;
    }

    public String getEngineer_id() {
        return engineer_id;
    }

    public void setEngineer_id(String engineer_id) {
        this.engineer_id = engineer_id;
    }

    public String getTrainees() {
        return trainees;
    }

    public void setTrainees(String trainees) {
        this.trainees = trainees;
    }

    public String getTrainee_position() {
        return trainee_position;
    }

    public void setTrainee_position(String trainee_position) {
        this.trainee_position = trainee_position;
    }

    public String getEngineer_remarks() {
        return engineer_remarks;
    }

    public void setEngineer_remarks(String engineer_remarks) {
        this.engineer_remarks = engineer_remarks;
    }

    public String getInstallation_date() {
        return installation_date;
    }

    public void setInstallation_date(String installation_date) {
        this.installation_date = installation_date;
    }

    public String getRecieversDate() {
        return recieversDate;
    }

    public void setRecieversDate(String recieversDate) {
        this.recieversDate = recieversDate;
    }

    public String getRecievers_name() {
        return recievers_name;
    }

    public void setRecievers_name(String recievers_name) {
        this.recievers_name = recievers_name;
    }

    public String getReceiver_designation() {
        return receiver_designation;
    }

    public void setReceiver_designation(String receiver_designation) {
        this.receiver_designation = receiver_designation;
    }

    public String getReceiver_comments() {
        return receiver_comments;
    }

    public void setReceiver_comments(String receiver_comments) {
        this.receiver_comments = receiver_comments;
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

    public String getNextservice() {
        return nextservice;
    }

    public void setNextservice(String nextservice) {
        this.nextservice = nextservice;
    }

    @Override
    public String getTitle() {
        return asset_name;
    }
}
