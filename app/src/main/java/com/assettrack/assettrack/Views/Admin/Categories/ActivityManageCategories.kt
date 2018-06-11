package com.assettrack.assettrack.Views.Admin.Categories


import android.app.Dialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import com.afollestad.materialdialogs.MaterialDialog
import com.androidnetworking.error.ANError
import com.assettrack.assettrack.Constatnts.APiConstants
import com.assettrack.assettrack.Data.PrefManager
import com.assettrack.assettrack.Data.Request
import com.assettrack.assettrack.Interfaces.UtilListeners.RequestListener
import com.assettrack.assettrack.Models.CategoriesModel
import com.assettrack.assettrack.R
import org.json.JSONObject
import java.util.*

class ActivityManageCategories : AppCompatActivity() {
    internal var fragment: Fragment? = null
    var fab: FloatingActionButton? = null
    var toolbar: android.support.v7.widget.Toolbar? = null
    internal lateinit var m: MaterialDialog
    lateinit var progress: ProgressDialog
    private lateinit var prefManager: PrefManager


    fun popOut() {
        popOutFragments()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_categories)
        setSupportActionBar(toolbar)
        prefManager = PrefManager(this)

        fab = this.findViewById(R.id.fab)
        fab?.setOnClickListener { view ->

            val wrapInScrollView = true
            m = MaterialDialog.Builder(this)
                    .title("New Category")
                    .customView(R.layout.dialog_category_details_edit, wrapInScrollView)
                    .positiveText("Save")
                    .autoDismiss(true)
                    .onPositive { dialog, which ->
                        var vi: View = m.customView!!
                        var tname: EditText = vi.findViewById(R.id.txt_customer_name)
                        if (TextUtils.isEmpty(tname.text.toString())) {
                            tname.requestFocus()
                            tname.error = "Required"

                        } else {

                            m.dismiss()
                            saveNew(tname.text)

                        }

                        //m.dismiss();
                    }
                    .show()
//            fragment= FragmentEdit()
//            popOutFragments()
//            setFragment()
        }
//        this.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
        fragment = FragmentCategoriesList()
        val bundle = Bundle()
        bundle.putInt("type", 0)
        bundle.putInt("STATUS_ID", 0)
        (fragment as FragmentCategoriesList).arguments = bundle
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment, "fragmentMain").commit()


        //setFab(R.drawable.ic_add_white_24dp,true)
    }

    private fun saveNew(text: Editable?) {


        progress = ProgressDialog(this)
        progress.setMessage("Working ...")
        progress.setCancelable(true)
        progress.isIndeterminate
        progress.setTitle("Search asset")
        progress.show()
        var url = APiConstants.addcategory

        var params: HashMap<String, String> = HashMap()
        params.put("Name", text.toString())
        Request.postRequest(url, params, prefManager.getToken(), object : RequestListener {
            override fun onError(error: ANError) {

                if (progress.isShowing) {
                    progress.setMessage(error.message)
                    progress.dismiss()
                }


            }

            override fun onError(error: String) {

                if (progress.isShowing) {
                    progress.setMessage(error)
                    progress.dismiss()
                }
            }

            override fun onSuccess(response: String) {
                if (progress.isShowing) {
                    progress.setMessage(response)
                    progress.dismiss()
                }


                onResume()
                Log.d("getData", response)

                try {

                    val jsonObject = JSONObject(response)

                } catch (nm: Exception) {
                    if (progress.isShowing) {
                        progress.setMessage(response)
                        progress.dismiss()


                    }
                    Log.d("getData", nm.toString())
                }


            }

        })


    }

    fun setFab(icon: Int, isVisible: Boolean) {
        fab?.setImageResource(icon)
        if (isVisible) {
            fab?.show()
        } else {
            fab?.hide()
        }
    }

    internal fun setFragment() {
        // fragment = new FragmentSearch();
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment, "fragmentMain").commit()
    }

    internal fun popOutFragments() {
        val fragmentManager = this.supportFragmentManager
        for (i in 0 until fragmentManager.backStackEntryCount) {
            fragmentManager.popBackStack()
        }
    }

    private fun startEditDialog(categoriesModel: CategoriesModel) {
        val layoutInflaterAndroid = LayoutInflater.from(this)
        val mView = layoutInflaterAndroid.inflate(R.layout.dialog_category_details_edit, null)
        val alertDialogBuilderUserInput = AlertDialog.Builder(Objects.requireNonNull(this))
        alertDialogBuilderUserInput.setView(mView)
        alertDialogBuilderUserInput.setTitle("Enter Category Details")

        val name: EditText
        val idd: EditText
        name = mView.findViewById(R.id.txt_customer_name)
        // idd = mView.findViewById(R.id.txt_customer_id);
        // name=mView.findViewById(R.id.txt_customer_name);


        name.setText(categoriesModel.name)
        // idd.setText(categoriesModel.getId());
        //name.setText(categoriesModel.getName());


        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Save") { dialogBox, id ->
                    // ToDo get user input here

                    // dialogBox.dismiss();

                }

                .setNegativeButton("Dismiss"
                ) { dialogBox, id -> dialogBox.cancel() }

        val alertDialogAndroid = alertDialogBuilderUserInput.create()
        alertDialogAndroid.show()

        val theButton = alertDialogAndroid.getButton(DialogInterface.BUTTON_POSITIVE)
        theButton.setOnClickListener(CustomListener(alertDialogAndroid, categoriesModel.id))

    }

    internal inner class CustomListener
    // private final CategoriesModel categoriesModel;


    (private val dialog: Dialog, private val keyid: Int) : View.OnClickListener {

        override fun onClick(v: View) {
            val name: EditText
            val email: EditText
            val phone: EditText
            val address: EditText
            val idd: EditText
            name = dialog.findViewById(R.id.txt_customer_name)
            // name=mView.findViewById(R.id.txt_customer_name);


            if (name.text.toString().isEmpty()) {
                name.error = "Required"
                name.requestFocus()
                return
            }


            val categoriesModel = CategoriesModel()
            categoriesModel.name = name.text.toString()
            categoriesModel.id = keyid
            newCategory(categoriesModel)

            dialog.dismiss()

            //            ContentValues contentValues = new ContentValues();
            //            contentValues.put(DbConstants.cust_name, categoriesModel.getName());
            //            contentValues.put(DbConstants.cust_id, categoriesModel.getName());
            //            contentValues.put(DbConstants.cust_email, categoriesModel.getEmail());
            //            contentValues.put(DbConstants.cust_address, categoriesModel.getEmail());
            //            contentValues.put(DbConstants.cust_physical_address, categoriesModel.getPhysical_address());
            //            contentValues.put(DbConstants.cust_telephone, categoriesModel.getTelephone());
            //
            //
            //            if (new DbOperations(getActivity()).update(DbConstants.TABLE_CLIENT, DbConstants.KEY_ID, keyid, contentValues)) {
            //                dialog.dismiss();
            //            }

        }


    }

    private fun newCategory(categoriesModel: CategoriesModel) {

    }

}
