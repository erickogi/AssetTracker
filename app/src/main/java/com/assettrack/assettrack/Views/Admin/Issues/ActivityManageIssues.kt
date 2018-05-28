package com.assettrack.assettrack.Views.Admin.Issues

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import com.assettrack.assettrack.Adapters.AssignmentSearchAdapter
import com.assettrack.assettrack.Interfaces.UtilListeners.OnclickRecyclerListener
import com.assettrack.assettrack.Models.AssetModel
import com.assettrack.assettrack.Models.EngineerModel
import com.assettrack.assettrack.Models.IssueModel
import com.assettrack.assettrack.R
import com.assettrack.assettrack.Views.Admin.Engineers.FragmentEdit
import kotlinx.android.synthetic.main.activity_manage_issues.*
import java.util.*

class ActivityManageIssues : AppCompatActivity() {
    internal var fragment: Fragment? = null
    internal var fragmentIssueList: FragmentIssueList? = null
    internal lateinit var mStaggeredLayoutManager: StaggeredGridLayoutManager
    internal var engineerModel: EngineerModel? = null
    internal var assetModel: AssetModel? = null

    var  fab: FloatingActionButton?=null
    var  toolbar: android.support.v7.widget.Toolbar?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_issues)
        toolbar=findViewById(R.id.toolbar)
        fab=findViewById(R.id.fab)

        setSupportActionBar(toolbar)


        fab?.setOnClickListener { view ->
            fragment= FragmentNewIssues()
            popOutFragments()
            setFragment()
        }
        fragment = FragmentIssueList()
        val bundle = Bundle()
        bundle.putInt("type", 0)
        bundle.putInt("STATUS_ID", 0)
        (fragment as FragmentIssueList).arguments = bundle
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment, "fragmentMain").commit()

    }
    public fun  setFab(icon:Int,isVisible: Boolean ){
        fab?.setImageResource(icon)
        if(isVisible){
            fab?.show()
        }else{
            fab?.hide()
        }
    }
    public fun popOut(){
        popOutFragments()
    }
    fun createIssue() {

    }

    private fun startEditDialog(engineerModels: ArrayList<EngineerModel>, assetModels: ArrayList<AssetModel>) {
        val layoutInflaterAndroid = LayoutInflater.from(this)
        val mView = layoutInflaterAndroid.inflate(R.layout.dialog_admin_new_issue, null)
        val alertDialogBuilderUserInput = AlertDialog.Builder(Objects.requireNonNull(this))
        alertDialogBuilderUserInput.setView(mView)
        alertDialogBuilderUserInput.setTitle("Issue Details")


        val assetId: EditText
        val engId: EditText
        val recyclerView: RecyclerView
        assetId = mView.findViewById(R.id.edt_asset_id)
        engId = mView.findViewById(R.id.edt_eng_id)

        recyclerView = mView.findViewById(R.id.recyclerView)

        assetId.setOnClickListener { v ->


            val searchAdapter = AssignmentSearchAdapter(this@ActivityManageIssues, assetModels, object : OnclickRecyclerListener {
                override fun onClickListener(position: Int) {
                    assetModel = assetModels[position]
                }

                override fun onLongClickListener(position: Int) {

                }

                override fun onCheckedClickListener(position: Int) {

                }

                override fun onMoreClickListener(position: Int) {

                }

                override fun onClickListener(adapterPosition: Int, view: View) {

                }
            })
            searchAdapter.notifyDataSetChanged()
            mStaggeredLayoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            recyclerView.layoutManager = mStaggeredLayoutManager
            recyclerView.itemAnimator = DefaultItemAnimator()
            recyclerView.adapter = searchAdapter
            searchAdapter.notifyDataSetChanged()


        }

        engId.setOnClickListener { v ->
            val searchAdapter = AssignmentSearchAdapter(1, engineerModels, object : OnclickRecyclerListener {
                override fun onClickListener(position: Int) {
                    engineerModel = engineerModels[position]
                }

                override fun onLongClickListener(position: Int) {

                }

                override fun onCheckedClickListener(position: Int) {

                }

                override fun onMoreClickListener(position: Int) {

                }

                override fun onClickListener(adapterPosition: Int, view: View) {

                }
            })
            searchAdapter.notifyDataSetChanged()
            mStaggeredLayoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            recyclerView.layoutManager = mStaggeredLayoutManager
            recyclerView.itemAnimator = DefaultItemAnimator()
            recyclerView.adapter = searchAdapter
            searchAdapter.notifyDataSetChanged()


        }



        assetId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {


            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {

            }
        })
        engId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {

            }
        })

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
        theButton.setOnClickListener(CustomListener(alertDialogAndroid))

    }


    internal inner class CustomListener(private val dialog: Dialog) : View.OnClickListener {

        override fun onClick(v: View) {


            if (engineerModel == null) {

                return
            }
            if (assetModel == null) {
                return
            }

            val issueModel = IssueModel()
            //val issueModel = IssueModel()
            issueModel.assets_id = assetModel!!.id.toString()
            issueModel.asset_code = assetModel!!.asset_code
            issueModel.customers_id = assetModel!!.customers_id
            issueModel.customers_id = assetModel!!.customers_id

//           issueModel.engineer_id = engineerModel!!.id.toString()
//            issueModel.setEngineer_name(engineerModel.getName())
//            issueModel.issue_status = "0"
//            issueModel.setWork_ticket(String.valueOf(GeneralUtills.getRandon(1000, 5000)))


            //            fieldsName.put(DbConstants.KEY_ID, "INTEGER PRIMARY KEY  AUTOINCREMENT");
            //            fieldsName.put(DbConstants.asset_id, "varchar ");
            //            fieldsName.put(DbConstants.message, "varchar ");
            //            fieldsName.put(DbConstants.date, "varchar ");
            //            fieldsName.put(DbConstants.issue, "varchar ");
            //            fieldsName.put(DbConstants.fix, "varchar ");
            //            fieldsName.put(DbConstants.comment, "varchar ");
            //            fieldsName.put(DbConstants.person, "varchar ");
            //
            //            fieldsName.put(DbConstants.parts_needed, "varchar ");
            //            fieldsName.put(DbConstants.parts_used, "varchar ");
            //
            //
            //            fieldsName.put(DbConstants.next_service, "varchar ");
            //
            //
            //            fieldsName.put(DbConstants.cusomerremarks, "varchar ");
            //            fieldsName.put(DbConstants.engineersremarks, "varchar ");
            //            fieldsName.put(DbConstants.labourhours, "varchar ");
            //            fieldsName.put(DbConstants.travelhours, "varchar ");
            //
//            Asset3
//            Engineer5
//            AssetCode123
//            CustomerNameEric
//            CustomerId1
//            StartDate
//            CloseDate
//            NextdueService
//            TravelHours
//            LabourHours
//            FailureDesc
//            FailurSolution
//            EngineerComment
//            CustomerComment
//            Safety
//            Status
//            PartsState

//            val contentValues = ContentValues()
//            contentValues.put(DbConstants.issuse_asset_id, issueModel.getAsset_id())
//            contentValues.put(DbConstants.issuse_asset_code, issueModel.getAsset_code())
//            contentValues.put(DbConstants.issuse_asset_name, issueModel.getAsset_name())
//            contentValues.put(DbConstants.issuse_customer_name, issueModel.getCustomer_name())
//            contentValues.put(DbConstants.issuse_customer_id, issueModel.getCustomer_id())
//            contentValues.put(DbConstants.issuse_engineer_id, issueModel.getEngineer_id())
//            contentValues.put(DbConstants.issuse_engineer_name, issueModel.getEngineer_name())
//            contentValues.put(DbConstants.issuse_date, issueModel.getDate())
//            contentValues.put(DbConstants.issuse_issue_status, issueModel.getIssue_status())
//            contentValues.put(DbConstants.issuse_work_ticket, issueModel.getWork_ticket())
//
//            //            contentValues.put(DbConstants.issuse_engineer_id, issueModel.getEngineer_id());
//            //           // contentValues.put(DbConstants.issue, issueModel.getFailure_desc());
//            //            contentValues.put(DbConstants.is, engineerModel.getLast_name());
//            //            contentValues.put(DbConstants.email, engineerModel.getEmail());
//            //            contentValues.put(DbConstants.speciality, engineerModel.getSpeciality());
//            //
//            //            contentValues.put(DbConstants.phone, engineerModel.getPhone());
//            //
//
//            if (DbOperations(this@ActivityManageIssues).insert(DbConstants.TABLE_ISSUE_V1, contentValues)) {
//                dialog.dismiss()
//
//                if (fragmentIssueList == null) {
//                    fragmentIssueList = supportFragmentManager.findFragmentByTag("fragmentMain") as FragmentIssueList
//                }
//                fragmentIssueList.refresh()
//            }

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
}
