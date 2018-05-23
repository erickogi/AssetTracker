package com.assettrack.assettrack.Data.Parsers

import com.assettrack.assettrack.Models.UserModel
import org.json.JSONException
import org.json.JSONObject

class UserParser {

    companion object {
        fun parse(response: String): UserModel {
            val userModel = UserModel()

            try {
                val jsonObject = JSONObject(response)
                val data = jsonObject.getJSONObject("data")
                val meta = jsonObject.getJSONObject("meta")
                userModel.setRole(data.getInt("role"))
                userModel.setCreated_at(data.getString("created_at"))
                userModel.setDesignation(data.getString("designation"))
                userModel.setEmail(data.getString("email"))
                userModel.setEmployeeid(data.getString("employeeid"))
                userModel.setFirstname(data.getString("firstname"))
                userModel.setLastname(data.getString("lastname"))
                userModel.setFull_name(data.getString("full_name"))
                userModel.setId(data.getInt("id"))
                userModel.setPhoneNumber(data.getString("phoneNumber"))
                userModel.setUpdated_at(data.getString("updated_at"))
                userModel.setToken(meta.getString("token"))
                userModel.setRolename(data.getString("rolename"))


            } catch (e: JSONException) {
                e.printStackTrace()
            }

            return userModel
        }
    }

}
