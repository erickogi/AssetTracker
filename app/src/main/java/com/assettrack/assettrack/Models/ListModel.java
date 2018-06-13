package com.assettrack.assettrack.Models;

import java.io.Serializable;

import ir.mirrajabi.searchdialog.core.Searchable;

public class ListModel implements Serializable, Searchable {

    private String label = "";
    private String value = "";

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getTitle() {
        return value;
    }
}
