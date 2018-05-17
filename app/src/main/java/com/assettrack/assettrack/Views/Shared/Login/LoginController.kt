package com.assettrack.assettrack.Views.Shared.Login

import android.content.Context
import android.widget.EditText
import com.assettrack.assettrack.Data.PrefManager

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
            return 0.toString()
        }
        if (edtPassword?.text.toString().isEmpty() || edtPassword?.text.toString() == "") {
            edtPassword?.error = "Required"
            edtPassword?.requestFocus()
            return 0.toString()
        }


        //TODO CHANGE THIS TO AN API CALL

        if (edtID?.text.toString().toLowerCase() == "admin" && edtPassword?.text.toString() == "zalego") {
            prefManager.setIsLoggedIn(true, 1)

            return 1.toString()

        }
        if (edtID?.text.toString().toLowerCase() == "engineer" && edtPassword?.text.toString() == "zalego") {
            prefManager.setIsLoggedIn(true, 2)

            return 2.toString()


        }
        return "Cannot login you in with those details"
    }
}