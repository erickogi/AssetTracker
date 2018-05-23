package com.assettrack.assettrack.Views.Engineer

import android.Manifest
import android.app.Dialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.androidnetworking.error.ANError
import com.assettrack.assettrack.Constatnts.APiConstants
import com.assettrack.assettrack.Data.PrefManager
import com.assettrack.assettrack.Data.Request
import com.assettrack.assettrack.Interfaces.UtilListeners.RequestListener
import com.assettrack.assettrack.R
import com.assettrack.assettrack.Views.Engineer.ListAsset.AssetList
import com.assettrack.assettrack.Views.Engineer.NewAsset.Installation
import com.assettrack.assettrack.Views.Shared.Login.LoginActivity
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScannerBuilder
import com.google.android.gms.vision.barcode.Barcode
import com.nightonke.boommenu.BoomMenuButton
import com.special.ResideMenu.ResideMenu
import com.special.ResideMenu.ResideMenuItem
import kotlinx.android.synthetic.main.activity_main.*

class EngineerMainActivity : AppCompatActivity() {
    private var resideMenu: ResideMenu? = null
    private val bmb: BoomMenuButton? = null
    private val img_fab: ImageView? = null
    internal var i: Int = 0
    lateinit var progress: ProgressDialog
    internal fun setResideMenu() {
        resideMenu = ResideMenu(this)
        resideMenu!!.setBackground(R.drawable.background_bf)
        resideMenu!!.attachToActivity(this)


        val resideMenuItem = ResideMenuItem(this, R.drawable.ic_home_black_24dp, "Home ")
        val resideMenuItem0 = ResideMenuItem(this, R.drawable.ic_add_bla_24dp, "Add Assets ")
        val resideMenuItem1 = ResideMenuItem(this, R.drawable.ic_account_circle_black_24dp, "Profile")
        val resideMenuItem2 = ResideMenuItem(this, R.drawable.ic_history_black_24dp, "History")
        val resideMenuItem3 = ResideMenuItem(this, R.drawable.ic_exit_to_app_black_24dp, "Log Out")

        resideMenu!!.addMenuItem(resideMenuItem, ResideMenu.DIRECTION_LEFT)
        resideMenu!!.addMenuItem(resideMenuItem3, ResideMenu.DIRECTION_LEFT)

        resideMenuItem.setOnClickListener {

            resideMenu!!.closeMenu()
        }
        resideMenuItem0.setOnClickListener {

            resideMenu!!.closeMenu()

        }
        resideMenuItem1.setOnClickListener {
            resideMenu!!.closeMenu()

        }
        resideMenuItem2.setOnClickListener {
            resideMenu!!.closeMenu()

        }
        resideMenuItem3.setOnClickListener {
            resideMenu!!.closeMenu()
            val prefManager = PrefManager(this)
            prefManager.setIsLoggedIn(false, 2)
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        resideMenu!!.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT)


    }

    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        prefManager = PrefManager(this)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        fab.hide()
        setResideMenu()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun startScan() {
        /**
         * Build a new MaterialBarcodeScanner
         */
        val materialBarcodeScanner = MaterialBarcodeScannerBuilder()
                .withActivity(this)
                .withBackfacingCamera()
                .withEnableAutoFocus(true)
                .withBleepEnabled(true)
                .withCenterTracker()
                //.withOnly2DScanning()
                .withBarcodeFormats(Barcode.AZTEC or Barcode.EAN_13 or Barcode.CODE_93 or Barcode.ALL_FORMATS)
                .withBackfacingCamera()
                 .withText("Scanning...")
                .withResultListener { barcode ->
//




                }
                .build()
        materialBarcodeScanner.startScan()

    }
    private fun startDialog() {

        val layoutInflaterAndroid = LayoutInflater.from(this)
        val mView = layoutInflaterAndroid.inflate(R.layout.dialog_search_asset, null)
        val alertDialogBuilderUserInput = AlertDialog.Builder(this)
        alertDialogBuilderUserInput.setView(mView)
        alertDialogBuilderUserInput.setTitle("Search By Item Code")
         alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Search") { dialogBox, id ->
                    // ToDo get user input here

                }

                .setNegativeButton("Dismiss"
                ) { dialogBox, id -> dialogBox.cancel() }

        val alertDialogAndroid = alertDialogBuilderUserInput.create()
        alertDialogAndroid.show()
        val theButton = alertDialogAndroid.getButton(DialogInterface.BUTTON_POSITIVE)
        theButton.setOnClickListener(CustomListener(alertDialogAndroid))

    }

    fun scan(view: View) {
        if (checkPerrmission()) {
            startScan()
        } else {
            requestPermissions()
        }
    }

    private fun startList() {

        startActivity(Intent(this, AssetList::class.java))

    }

    private fun searchByCode() {
        startDialog()
    }

    fun entercode(view: View) {
        searchByCode()
    }

    fun assetList(view: View) {
        startList()
    }

    fun addNewAssetr(view: View) {}

    fun addNewAsset(view: View) {
        var intent = Intent(this, Installation::class.java)
        intent.putExtra("state", 2)
        startActivity(intent)
        //
    }

    fun assignments(view: View) {

    }

    internal inner class CustomListener(private val dialog: Dialog) : View.OnClickListener {

        override fun onClick(v: View) {
            val edtCode: EditText

            edtCode = dialog.findViewById(R.id.edt_code)

            if (edtCode.text.toString().isEmpty()) {
                edtCode.error = "Required"
                return
            }



            searchAsset(edtCode.text)


        }


    }

    private fun searchAsset(text: Editable?) {
        progress = ProgressDialog(this)
        progress.setMessage("Working ...")
        progress.setCancelable(false)
        progress.isIndeterminate
        progress.setTitle("Search asset")
        progress.show()

        var params: HashMap<String, String> = HashMap()
        var url = APiConstants.searchasset

        Request.postRequest(url, params, prefManager.getToken(), object : RequestListener {
            override fun onError(error: ANError) {

                progress.dismiss()

            }

            override fun onError(error: String) {

                progress.dismiss()

            }

            override fun onSuccess(response: String) {


            }

        })

    }


    private fun checkPerrmission(): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 100)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults.size > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startScan()
            } else {
                permisionDeneied()
            }


        }
    }


    private fun permisionDeneied() {
        val dialog = Dialog(this)
        dialog.setCancelable(false)
        dialog.setTitle("Permission Denied")
        //dialog.setM

    }
}
