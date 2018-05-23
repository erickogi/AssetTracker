package com.assettrack.assettrack.Constatnts

class APiConstants {

    companion object {
        var baseUrl: String = "http://asset-track.photozuri.com/api/"
        var searchasset: String = baseUrl + ""

        var login: String = baseUrl + "auth/login"
        var register: String = baseUrl + "auth/"

        var allAssets: String = baseUrl + "assets"
        var allCustomers: String = baseUrl + "customers"
        var allEngineers: String = baseUrl + "engineers"
        var allIssues: String = baseUrl + "issues"
        var faultyAssets: String = baseUrl + "faulty"
        var workingAssets: String = baseUrl + "working"
        var groundedAssets: String = baseUrl + "grounded"
        var getIssues: String = baseUrl + "issue"

        //assets/
        var customerlist: String = baseUrl + "allcustomerlist"
        var updateAsset: String = baseUrl + "assets/"
        var updateCustomer: String = baseUrl + "customer/"
        var updateEngineer: String = baseUrl + "customer/"
        var createAsset: String = baseUrl + "asset/store"
        var createIssue: String = baseUrl + "issue/store"

    }



}