package com.assettrack.assettrack.Data.Parsers;

import com.assettrack.assettrack.Models.Counts;

import org.json.JSONObject;

public class CountsParser {

    public static Counts parse(JSONObject response) {
        Counts counts = new Counts();

//        {
//            "acount": 1,
//                "gcount": 0,
//                "wcount": 1,
//                "fcount": 0,
//                "ccount": 3,
//                "ecount": 8,
//                "oissues": 0,
//                "pissues": 0,
//                "cissues": 0,
//                "error": false
//        }
        try {
            counts.setAcount(response.optInt("acount"));
            counts.setGcount(response.optInt("gcount"));
            counts.setWcount(response.optInt("wcount"));
            counts.setFcount(response.optInt("fcount"));
            counts.setCcount(response.optInt("ccount"));
            counts.setEcount(response.optInt("ecount"));

            counts.setOissues(response.optInt("oissues"));
            counts.setPissues(response.optInt("pissues"));
            counts.setCissues(response.optInt("cissues"));
            counts.setError(response.optBoolean("error"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return counts;
    }
}
