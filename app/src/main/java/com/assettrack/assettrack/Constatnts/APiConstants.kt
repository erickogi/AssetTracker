package com.assettrack.assettrack.Constatnts

class APiConstants {

    companion object {
        var baseUrl: String = "http://asset-track.photozuri.com/api/"
        var baseImgUrl: String = "http://asset-track.photozuri.com/public/img/assets/"
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
        var globalCount: String = baseUrl + "globalcount"
        var getIssues: String = baseUrl + "issue"
        //i/issue/5/update
        //assets/
        // users/5/update
        var customerlist: String = baseUrl + "allcustomerlist"

        //categories
        var categorieslist: String = baseUrl + "categories"
        var addcategory: String = baseUrl + "category/store"
        var updatecategory: String = baseUrl + "category/" // id // update
        var deletecategory: String = baseUrl + "category/delete" // id




        var updateAsset: String = baseUrl + "assets/"
        var updateIssue: String = baseUrl + "issue/"
        var updateWorkticket: String = baseUrl + "changeworkstate"
        var updateCustomer: String = baseUrl + "customer/"
        var updateEngineer: String = baseUrl + "users/"
        var createAsset: String = baseUrl + "asset/create"
        var createIssue: String = baseUrl + "issue/create"
        var createCustomer: String= baseUrl+"customer/store"
        var createEngineer: String= baseUrl+"newuser"


        var seachClientsList: String = baseUrl + "allcustomerlist"
        var seachAssetsList: String = baseUrl + "allpartslist"
        var seachEngineersList: String = baseUrl + "allcustomerlist"
        var seachIssuesList: String = baseUrl + "allcustomerlist"
        //asset/delete/2
        var deleteAsset: String = baseUrl + "asset/delete/"
        var deleteCustomer: String = baseUrl + "customer/delete/"
        var deleteIssue: String = baseUrl + "issue/delete/"
        var deleteEngineer: String = baseUrl + "user/delete/"
    }



}