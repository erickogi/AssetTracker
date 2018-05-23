package com.assettrack.assettrack.Data

import android.content.Context
import android.content.SharedPreferences
import com.assettrack.assettrack.Models.UserModel

class PrefManager {
    private var Kid: String = "id"
    private var Kfirstname: String? = "firstname"
    private var Klastname: String? = "lastname"
    private var Kemployeeid: String? = "employeeid"
    private var Krole: String = "role"
    private var Kemail: String? = "email"
    private var Kdesignation: String? = "designation"
    private var Kcreated_at: String? = "created_at"
    private var Kupdated_at: String? = "updated_at"
    private var Kfull_name: String? = "full_name"
    private var Krolename: String? = "rolename"
    private var Ktoken: String? = "token"
    private var KphoneNumber: String? = "phoneNumber"

    private var KEY_IS_LOGGED_IN="isloggedin"
    private var PREF_NAME:String?="PREFNAME"

    // Shared Preferences
    internal var pref: SharedPreferences
    // Editor for Shared preferences
    internal var editor: SharedPreferences.Editor
    // Context
    internal var _context: Context
    // Shared pref mode
    internal var PRIVATE_MODE = 0

    constructor(_context: Context) {
        this._context = _context
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }


    fun getToken(): String {
        return pref.getString(Ktoken, "")
    }

    fun isLoggedIn(): Boolean {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun getUserType(): Int {
        return pref.getInt(Krole, 1)
    }

    fun setIsLoggedIn(isLoggedIn: Boolean, role: Int) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
        editor.putInt(Krole, role)
        editor.commit()
    }

    fun getUserData(): UserModel {
        val userData = UserModel()

        userData.setRole(pref.getInt(Krole,0))
        userData.setCreated_at(pref.getString(Kcreated_at,null))
        userData.setDesignation(pref.getString(Kdesignation,null))
        userData.setEmail(pref.getString(Kemail,null))
        userData.setEmployeeid(pref.getString(Kemployeeid,null))
        userData.setFirstname(pref.getString(Kfirstname,null))
        userData.setLastname(pref.getString(Klastname,null))
        userData.setFull_name(pref.getString(Kfull_name, null))
        userData.setId(pref.getInt(Kid,0))
        userData.setToken(pref.getString(Ktoken,null))
        userData.setUpdated_at(pref.getString(Kupdated_at,null))
        userData.setPhoneNumber(pref.getString(KphoneNumber, null))
        userData.setRolename(pref.getString(Krolename, null))

        return userData
    }

    ///SET AND GET USER DATA
    fun setUserData(userData: UserModel) {


        editor.putInt(Krole, userData.getRole())
        editor.putString(Kcreated_at, userData.getCreated_at())
        editor.putString(Kdesignation, userData.getDesignation())
        editor.putString(Kemail, userData.getEmail())
        editor.putString(Kemployeeid, userData.getEmployeeid())
        editor.putString(Kfirstname, userData.getFirstname())
        editor.putString(Klastname, userData.getLastname())
        editor.putString(Kfull_name, userData.getFull_name())
        editor.putInt(Kid, userData.getId())
        editor.putString(Ktoken, userData.getToken())
        editor.putString(Kupdated_at, userData.getUpdated_at())
        editor.putString(KphoneNumber, userData.getPhoneNumber())
        editor.putString(Krolename, userData.getCreated_at())

        editor.commit()

    }


}