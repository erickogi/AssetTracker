package com.assettrack.assettrack.Views.Admin.Clients

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.assettrack.assettrack.Models.CustomerModel
import com.assettrack.assettrack.R
import kotlinx.android.synthetic.main.activity_manage_clients.*

class ActivityManageClients : AppCompatActivity() {
    internal var fragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_clients)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            startEditDialog(CustomerModel())
        }

        fragment = FragmentClientList()
        val bundle = Bundle()
        bundle.putInt("type", 0)
        bundle.putInt("STATUS_ID", 0)
        (fragment as FragmentClientList).arguments = bundle
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment, "fragmentMain").commit()


    }

    private fun startEditDialog(customerModel: CustomerModel) {
        val layoutInflaterAndroid = LayoutInflater.from(this)
        val mView = layoutInflaterAndroid.inflate(R.layout.dialog_client_details_edit, null)
        val alertDialogBuilderUserInput = AlertDialog.Builder(this)
        alertDialogBuilderUserInput.setView(mView)
        alertDialogBuilderUserInput.setTitle("Customer Details")

        val name: EditText
        val email: EditText
        val phone: EditText
        val address: EditText
        val idd: EditText
        val iddLabel: TextView
        name = mView.findViewById(R.id.txt_customer_name)
        email = mView.findViewById(R.id.txt_customer_email)
        phone = mView.findViewById(R.id.txt_customer_phone_no)
        address = mView.findViewById(R.id.txt_customer_location)
        idd = mView.findViewById(R.id.txt_customer_id)
        idd.visibility = View.GONE
        iddLabel = mView.findViewById(R.id.idlabel)
        iddLabel.visibility = View.GONE
        // name=mView.findViewById(R.id.txt_customer_name);


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
        theButton.setOnClickListener(CustomListener(alertDialogAndroid, customerModel.id))

    }

    internal inner class CustomListener(private val dialog: Dialog, private val keyid: Int) : View.OnClickListener {

        override fun onClick(v: View) {
            val name: EditText
            val email: EditText
            val phone: EditText
            val address: EditText
            val idd: EditText
            name = dialog.findViewById(R.id.txt_customer_name)
            email = dialog.findViewById(R.id.txt_customer_email)
            phone = dialog.findViewById(R.id.txt_customer_phone_no)
            address = dialog.findViewById(R.id.txt_customer_location)
            idd = dialog.findViewById(R.id.txt_customer_id)
            // name=mView.findViewById(R.id.txt_customer_name);


            if (name.text.toString().isEmpty()) {
                name.error = "Required"
                name.requestFocus()
                return
            }

            if (email.text.toString().isEmpty()) {
                email.error = "Required"
                email.requestFocus()
                return
            }

            if (phone.text.toString().isEmpty()) {
                phone.error = "Required"
                phone.requestFocus()
                return
            }

            if (address.text.toString().isEmpty()) {
                address.error = "Required"
                address.requestFocus()
                return
            }
            if (idd.text.toString().isEmpty()) {
                idd.error = "Required"
                idd.requestFocus()
                return
            }


            val customerModel = CustomerModel()
            customerModel.name = name.text.toString()
            //customerModel.setId(idd.getText().toString());
            customerModel.physical_address = address.text.toString()
            customerModel.address = email.text.toString()
            customerModel.address = email.text.toString()
            customerModel.telephone = phone.text.toString()

            dialog.dismiss()
            addNew(customerModel)

            //            ContentValues contentValues = new ContentValues();
            //            contentValues.put(DbConstants.cust_name, customerModel.getName());
            //            contentValues.put(DbConstants.cust_id, customerModel.getName());
            //            contentValues.put(DbConstants.cust_email, customerModel.getEmail());
            //            contentValues.put(DbConstants.cust_address, customerModel.getEmail());
            //            contentValues.put(DbConstants.cust_physical_address, customerModel.getPhysical_address());
            //            contentValues.put(DbConstants.cust_telephone, customerModel.getTelephone());
            //
            //
            //            if (new DbOperations(getActivity()).update(DbConstants.TABLE_CLIENT, DbConstants.KEY_ID, keyid, contentValues)) {
            //                dialog.dismiss();
            //            }

        }


    }

    private fun addNew(customerModel: CustomerModel) {


    }

}
