package com.assettrack.assettrack.Views.Shared.Login

import android.content.Context
import android.util.Log
import android.widget.EditText
import com.androidnetworking.error.ANError
import com.assettrack.assettrack.Constatnts.APiConstants
import com.assettrack.assettrack.Data.Parsers.UserParser
import com.assettrack.assettrack.Data.PrefManager
import com.assettrack.assettrack.Data.Request.Companion.postRequest
import com.assettrack.assettrack.Models.UserModel
import com.assettrack.assettrack.Utils.NetworkUtils
import org.json.JSONObject
import java.util.*

class LoginController {

    private lateinit var prefManager: PrefManager
    private lateinit var context: Context

    constructor(prefManager: PrefManager) {
        this.prefManager = prefManager
    }

    constructor(context: Context) {
        this.context = context
        prefManager = PrefManager(context)
    }


    fun login(edtID: EditText?, edtPassword: EditText?): String {

        if (edtID?.text.toString().isEmpty() || edtID?.text.toString() == "") {
            edtID?.requestFocus()
            edtID?.error = "Required"
            return "0"
        }
        if (edtPassword?.text.toString().isEmpty() || edtPassword?.text.toString() == "") {
            edtPassword?.error = "Required"
            edtPassword?.requestFocus()
            return "0"
        }


        var returns = ""
        //TODO CHANGE THIS TO AN API CALL
        val params = HashMap<String, String>()


        params["email"] = edtID?.text.toString()
        params["password"] = edtPassword?.text.toString()


        if (NetworkUtils.isConnectionFast(context)) {
            postRequest(APiConstants.login, params, prefManager.getToken(), object : com.assettrack.assettrack.Interfaces.UtilListeners.RequestListener {
                override fun onError(error: ANError) {
                    // Log.d("ktlogin",error.toString());

                    // Log.d("logintag", error.message)

                    returns = error.message!!
                    val errors = "Logging In was unsuccessful"
                    try {
                        val jsonObject = JSONObject(error.message)
                        if (jsonObject.opt("errors") != null) {
                            val err = jsonObject.getJSONObject("errors")

                            if (err.opt("root") != null) {
                                returns = err.getString("root")
                            } else if (err.opt("email") != null) {
                                returns = err.getJSONArray("email").optString(0)
                            } else if (err.opt("password") != null) {
                                returns = err.getJSONArray("password").optString(0)
                            } else {
                                returns = errors
                            }
                        }

                    } catch (nm: Exception) {
                        nm.printStackTrace()
                        Log.d("logintag", nm.toString())

                        returns = errors
                    }


                    prefManager.setIsLoggedIn(false, 0)

                }

                override fun onError(error: String) {

                    Log.d("ktlogin", error)

                    returns = error
                    val errors = "Logging In was unsuccessful"
                    try {
                        val jsonObject = JSONObject(error)
                        if (jsonObject.opt("errors") != null) {
                            val err = jsonObject.getJSONObject("errors")

                            if (err.opt("root") != null) {
                                returns = err.getString("root")
                            } else if (err.opt("email") != null) {
                                returns = err.getJSONArray("email").optString(0)
                            } else if (err.opt("password") != null) {
                                returns = err.getJSONArray("password").optString(0)
                            } else {
                                returns = errors
                            }
                        }

                    } catch (nm: Exception) {
                        nm.printStackTrace()
                        Log.d("logintag", nm.toString())

                        returns = errors
                    }


                    prefManager.setIsLoggedIn(false, 0)

                }

                override fun onSuccess(response: String) {


                    Log.d("ktlogin", response)
                    val js = JSONObject(response)
                    returns = if (!js.getBoolean("error")) {
                        val userModel: UserModel = UserParser.parse(response)
                        prefManager.setUserData(userModel)
                        prefManager.setIsLoggedIn(true, 1)
                        userModel.getRole().toString()
                    } else {
                        "Error login in"
                    }
                    Log.d("ktlogin", returns)
                }

            })

        } else {
            returns = "Slow or no intent connection"
        }

        return returns
    }
}