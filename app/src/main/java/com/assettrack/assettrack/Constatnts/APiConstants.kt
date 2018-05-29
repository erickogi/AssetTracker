package com.assettrack.assettrack.Constatnts

class APiConstants {

    companion object {
        var baseUrl: String = "http://asset-track.photozuri.com/api/"
        var searchasset: String = baseUrl + ""

        var login: String = baseUrl + "auth/login"
        var register: String = baseUrl + "auth/"
        var allAssets: String = baseUrl + "assets"

        var allAssetsByCustomer: String = baseUrl + "siglecustomer/"
        var assetByCode: String = baseUrl + "showasset/"
        var allCustomers: String = baseUrl + "customers"
        var allEngineers: String = baseUrl + "engineers"
        var allIssues: String = baseUrl + "issues"
        var allIssuesEngineer: String = baseUrl + "engineerswork"
        var faultyAssets: String = baseUrl + "faulty"
        var workingAssets: String = baseUrl + "working"
        var groundedAssets: String = baseUrl + "grounded"
        var getIssues: String = baseUrl + "issue"
        //i/issue/5/update
        //assets/
        // users/5/update
        var customerlist: String = baseUrl + "allcustomerlist"
        var updateAsset: String = baseUrl + "assets/"
        var updateIssue: String = baseUrl + "issue/"
        var updateWorkticket: String = baseUrl + "changeworkstate"
        var updateCustomer: String = baseUrl + "customer/"
        var updateEngineer: String = baseUrl + "users/"
        var createAsset: String = baseUrl + "asset/create"
        var createIssue: String = baseUrl + "issue/create"
        var createCustomer: String= baseUrl+"customer/store"
        var createEngineer: String= baseUrl+"newuser"


    }



}