package com.assettrack.assettrack.Models;

import java.io.Serializable;

public class Counts implements Serializable {


    private int acount;// All assets
    private int gcount;//Grounded assets

    private int wcount;//Working Assets
    private int fcount;//Faulty Assets
    private int ccount;//Customers Count
    private int ecount;//Engs Count
    private int oissues;//Open Issues
    private int pissues;//Progress Issue
    private int cissues;//Closed Issues
    private boolean error;


    public int getAcount() {
        return acount;
    }

    public void setAcount(int acount) {
        this.acount = acount;
    }

    public int getGcount() {
        return gcount;
    }

    public void setGcount(int gcount) {
        this.gcount = gcount;
    }

    public int getWcount() {
        return wcount;
    }

    public void setWcount(int wcount) {
        this.wcount = wcount;
    }

    public int getFcount() {
        return fcount;
    }

    public void setFcount(int fcount) {
        this.fcount = fcount;
    }

    public int getCcount() {
        return ccount;
    }

    public void setCcount(int ccount) {
        this.ccount = ccount;
    }

    public int getEcount() {
        return ecount;
    }

    public void setEcount(int ecount) {
        this.ecount = ecount;
    }

    public int getOissues() {
        return oissues;
    }

    public void setOissues(int oissues) {
        this.oissues = oissues;
    }

    public int getPissues() {
        return pissues;
    }

    public void setPissues(int pissues) {
        this.pissues = pissues;
    }

    public int getCissues() {
        return cissues;
    }

    public void setCissues(int cissues) {
        this.cissues = cissues;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
