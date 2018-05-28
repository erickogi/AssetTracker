package com.assettrack.assettrack.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class CustomerModel implements Serializable {

    private int id=0;
    private String name="";
    private String address="";
    private String telephone="";
    private String physical_address="";
    private String created_at="";
    private String updated_at="";
    private boolean isChecked;
    private int assetCount;

    private ArrayList<AssetModel> assetModels=new ArrayList<>();

    public ArrayList<AssetModel> getAssetModels() {
        return assetModels;
    }

    public void setAssetModels(ArrayList<AssetModel> assetModels) {
        this.assetModels = assetModels;
    }

    public int getAssetCount() {
        return assetCount;
    }

    public void setAssetCount(int assetCount) {
        this.assetCount = assetCount;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPhysical_address() {
        return physical_address;
    }

    public void setPhysical_address(String physical_address) {
        this.physical_address = physical_address;
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
}
