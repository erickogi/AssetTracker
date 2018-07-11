package com.assettrack.assettrack.Views.Shared.Login

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View

import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.androidnetworking.error.ANError
import com.assettrack.assettrack.Constatnts.APiConstants
import com.assettrack.assettrack.Data.Parsers.UserParser
import com.assettrack.assettrack.Data.PrefManager
import com.assettrack.assettrack.Data.Request
import com.assettrack.assettrack.Interfaces.UtilListeners.RequestListener
import com.assettrack.assettrack.Models.UserModel
import com.assettrack.assettrack.R
import com.assettrack.assettrack.Utils.MyToast
import com.assettrack.assettrack.Utils.NetworkUtils
import com.assettrack.assettrack.Views.Admin.Assets.ActivityManageAssets
import com.assettrack.assettrack.Views.Engineer.EngineerMainActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import java.util.*

class LoginActivity : AppCompatActivity() {
    private var edtID: TextInputEditText? = null
    private var edtPassword: TextInputEditText? = null
    private var dialog: MaterialDialog? = null

    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(toolbar)
        try {

            prefManager = PrefManager(this)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        fab.hide()

        if (prefManager.isLoggedIn()) {
            if (prefManager.getUserType() == 1) {
                startActivity(Intent(this, ActivityManageAssets::class.java))
                finish()
            } else if (prefManager.getUserType() == 2) {
                startActivity(Intent(this, EngineerMainActivity::class.java))
                finish()
            }
        }

        this.edtPassword = this.findViewById(R.id.input_password)
        this.edtID = findViewById(R.id.input_id)

        try {


            this.edtID?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.email, 0, 0, 0)
            this.edtPassword?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.password, 0, 0, 0)

        } catch (hj: Exception) {

            hj.printStackTrace()
        }

        } catch (hf: Exception) {

            hf.printStackTrace()
        }
    }

    private fun snack(msg: String) {
        Snackbar.make(findViewById(R.id.login_layout), msg, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show()
    }

    fun loginClicked(view: View) {
        try {
            login(edtID, edtPassword)
        } catch (hf: Exception) {

            hf.printStackTrace()
        }



    }

    fun login(edtID: TextInputEditText?, edtPassword: TextInputEditText?): String {

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

        val builder = MaterialDialog.Builder(this@LoginActivity)
                .title("Verifying you")
                .content("Please Wait")
                .cancelable(true)
                .progress(true, 0)


        dialog = builder.build()
        this.dialog?.show()
        var returns = ""
        var returnsrole = 0

        val params = HashMap<String, String>()


        params["email"] = edtID?.text.toString()
        params["password"] = edtPassword?.text.toString()


        if (NetworkUtils.isConnectionFast(this)) {
            Request.postRequest(APiConstants.login, params, prefManager.getToken(), object : RequestListener {
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

                    dialog?.setContent(returns)

                    snack(returns)
                    dialog?.dismiss()

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

                    dialog?.setContent(returns)

                    snack(returns)
                    dialog?.dismiss()
                    prefManager.setIsLoggedIn(false, 0)

                }

                override fun onSuccess(response: String) {
                    dialog?.dismiss()


                    Log.d("ktlogin", response)
                    val js = JSONObject(response)
                    returnsrole = if (!js.getBoolean("error")) {
                        val userModel: UserModel = UserParser.parse(response)
                        prefManager.setUserData(userModel)
                        prefManager.setIsLoggedIn(true, userModel.getRole())
                        userModel.getRole()
                    } else {
                        0
                    }

                    Log.d("returns", "role " + returns)
                    if (returnsrole == 1) {
                        dialog?.dismiss()

                        startActivity(Intent(this@LoginActivity, ActivityManageAssets::class.java))
                        finish()
                        return
                    }
                    if (returnsrole == 2) {
                        dialog?.dismiss()

                        startActivity(Intent(this@LoginActivity, EngineerMainActivity::class.java))
                        finish()
                        return
                    } else {
                        dialog?.setContent(returns)

                        snack(returns)
                        dialog?.dismiss()
                        MyToast.toast(returns, this@LoginActivity, R.drawable.ic_error_outline_black_24dp, Toast.LENGTH_LONG)
                    }
                }

            })

        } else {
            returns = "Slow or no intent connection"
        }

        return returns
    }




}
