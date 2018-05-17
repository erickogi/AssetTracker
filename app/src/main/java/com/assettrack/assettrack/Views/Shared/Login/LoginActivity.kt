package com.assettrack.assettrack.Views.Shared.Login

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.assettrack.assettrack.Data.PrefManager
import com.assettrack.assettrack.Views.Engineer.EngineerMainActivity
import com.assettrack.assettrack.R
import com.assettrack.assettrack.Utils.MyToast
import com.assettrack.assettrack.Views.Admin.Assets.ActivityManageAssets

import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private var edtID: EditText? = null
    private var edtPassword:EditText? = null

    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(toolbar)
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

        this.edtPassword = findViewById(R.id.input_password)
        this.edtID = findViewById(R.id.input_id)
    }

    fun loginClicked(view: View) {
      val res: String =  LoginController(this).login(edtID,edtPassword)


        if(res == "0")

        if (res == "1") {

            startActivity(Intent(this, ActivityManageAssets::class.java))
            finish()
        }
        if (res == "2") {

            startActivity(Intent(this, EngineerMainActivity::class.java))
            finish()
        }
        else{
            MyToast.toast(res,this,R.drawable.ic_error_outline_black_24dp,Toast.LENGTH_LONG)
        }


    }






}
